package mk.ukim.finki.wpaud.web.controller;

import mk.ukim.finki.wpaud.model.ShoppingCart;
import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.service.ShoppingCartService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class  ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

        String user= req.getRemoteUser();
        ShoppingCart cart= shoppingCartService.getActiveShoppingCart(user);
        model.addAttribute("cart",cart);
        model.addAttribute("bodyContent","shopping-cart");
      return "master-template";
    }

    @PostMapping("/add-product/{id}")
    public String addProductShoppingCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication){
        try {
            String user = req.getRemoteUser();
            ShoppingCart cart= shoppingCartService.addProductToShoppingCart(user,id);
            return "redirect:/shopping-cart";
        }catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+exception.getMessage();
        }
    }

    @DeleteMapping("/delete/{index}")
    public String deleteProductFromShoppingCart(@PathVariable int index, HttpServletRequest req){
        try {
            String user = req.getRemoteUser();
            shoppingCartService.deleteProductFromShoppingCart(user,index);
            return "redirect:/shopping-cart";
        }catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+exception.getMessage();
        }
    }

}
