package mk.ukim.finki.wpaud.web.controller;

import mk.ukim.finki.wpaud.dto.ProductDto;
import mk.ukim.finki.wpaud.model.Category;
import mk.ukim.finki.wpaud.model.Manufacturer;
import mk.ukim.finki.wpaud.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wpaud.service.CategoryService;
import mk.ukim.finki.wpaud.service.ManufacturerService;
import mk.ukim.finki.wpaud.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

     public ProductController(ProductService productService, ManufacturerService manufacturerService, CategoryService categoryService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }


    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error",error);
        }
        model.addAttribute("products",productService.findAll());
        model.addAttribute("bodyContent","products");
        return "master-template";
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return "redirect:/products";
    }


    @PostMapping("/edit-form")
    public String editProductPage(@RequestParam(required = false) Long id,Model model){
        List<Manufacturer> manufacturerList = manufacturerService.findAll();
        List<Category> categoryList = categoryService.listCategories();
        model.addAttribute("manufacturers", manufacturerList);
        model.addAttribute("categories", categoryList);
        if(id != null && productService.findById(id).isPresent()) {
            model.addAttribute("product", productService.findById(id).get());
            model.addAttribute("bodyContent","add-product");
            return "master-template";
        }
        return "redirect:/products?error=Product not found!";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("add-form")
    public String addProductPage(Model model){
        List<Manufacturer> manufacturerList = manufacturerService.findAll();
        List<Category> categoryList = categoryService.listCategories();
        model.addAttribute("manufacturers", manufacturerList);
        model.addAttribute("categories", categoryList);
        model.addAttribute("bodyContent","add-product");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam Long category,
                              @RequestParam Long manufacturer){
        ProductDto productDto = new ProductDto(name,price,quantity,category,manufacturer);

        if(id != null){
            productService.edit(id,name,price,quantity,category,manufacturer);
        }else{
            productService.save(productDto);
        }

        return "redirect:/products";
    }
}
