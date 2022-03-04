package mk.ukim.finki.wpaud.service.impl;

import mk.ukim.finki.wpaud.model.Product;
import mk.ukim.finki.wpaud.model.ShoppingCart;
import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.wpaud.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.wpaud.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wpaud.model.exceptions.ShoppingCartNotFound;
import mk.ukim.finki.wpaud.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpaud.repository.impl.jpa.ShoppingCartRepository;
import mk.ukim.finki.wpaud.repository.impl.jpa.UserRepository;
import mk.ukim.finki.wpaud.service.ProductService;
import mk.ukim.finki.wpaud.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long id) {
        if(!this.shoppingCartRepository.findById(id).isPresent())
            throw new ShoppingCartNotFound(id);

        return  this.shoppingCartRepository.findById(id).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user=userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return shoppingCartRepository.findByUserAndStatus(user,ShoppingCartStatus.CREATED)
                .orElseGet(()->{
                    ShoppingCart shoppingCart=new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });


    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart=getActiveShoppingCart(username);
        Product product = productService.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));

        if(shoppingCart.getProducts()
                .stream().filter(r->r.getId().equals(productId))
                .collect(Collectors.toList()).size()>0)
            throw new ProductAlreadyInShoppingCartException(productId,username);

        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);

        }

    @Override
    public void deleteProductFromShoppingCart(String username, int i) {
        ShoppingCart shoppingCart=getActiveShoppingCart(username);
        shoppingCart.getProducts().remove(i);
        shoppingCartRepository.save(shoppingCart);

    }


}
