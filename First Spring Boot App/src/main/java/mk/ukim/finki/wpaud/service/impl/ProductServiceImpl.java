package mk.ukim.finki.wpaud.service.impl;

import mk.ukim.finki.wpaud.dto.ProductDto;
import mk.ukim.finki.wpaud.model.Category;
import mk.ukim.finki.wpaud.model.Manufacturer;
import mk.ukim.finki.wpaud.model.Product;
import mk.ukim.finki.wpaud.model.exceptions.CategoryNotFound;
import mk.ukim.finki.wpaud.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.wpaud.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wpaud.repository.impl.jpa.CategoryRepository;
import mk.ukim.finki.wpaud.repository.impl.jpa.ManufacturerRepository;
import mk.ukim.finki.wpaud.repository.impl.jpa.ProductRepository;
import mk.ukim.finki.wpaud.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository inMemoryProductRepository;
    private final CategoryRepository inMemoryCategoryRepository;
    private final ManufacturerRepository inMemoryManufacturerRepository;

    public ProductServiceImpl(ProductRepository inMemoryProductRepository, CategoryRepository inMemoryCategoryRepository, ManufacturerRepository inMemoryManufacturerRepository) {
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemoryCategoryRepository = inMemoryCategoryRepository;
        this.inMemoryManufacturerRepository = inMemoryManufacturerRepository;
    }


    @Override
    public List<Product> findAll() {
        return inMemoryProductRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return inMemoryProductRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return inMemoryProductRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Product> save(ProductDto productDto) {
        Category category=inMemoryCategoryRepository.findById(productDto.getCategory()).orElseThrow(()->new CategoryNotFound(productDto.getCategory()));
        Manufacturer manufacturer=inMemoryManufacturerRepository.findById(productDto.getManufacturer()).orElseThrow(()->new ManufacturerNotFoundException(productDto.getManufacturer()));

        inMemoryProductRepository.deleteByName(productDto.getName());
        return Optional.of(inMemoryProductRepository.save(new Product(productDto.getName(), productDto.getPrice(), productDto.getQuantity(), category,manufacturer)));
    }


    @Override
    @Transactional
    public Optional<Product> edit(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {

        Product product = this.inMemoryProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        Category category = this.inMemoryCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFound(categoryId));
        product.setCategory(category);

        Manufacturer manufacturer = this.inMemoryManufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        product.setManufacturer(manufacturer);

        return Optional.of(this.inMemoryProductRepository.save(product));
    }


    @Override
    public void deleteById(Long id) {
        inMemoryProductRepository.deleteById(id);
    }
}
