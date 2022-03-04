package mk.ukim.finki.wpaud.web.controller;

import mk.ukim.finki.wpaud.service.CategoryService;
import mk.ukim.finki.wpaud.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categoryAndManufacturer")
public class CategoryAndManufacturerAddController {

    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public CategoryAndManufacturerAddController(CategoryService categoryService, ManufacturerService manufacturerService) {
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/categoryForm")
    public String getCategoryForm (Model model){
        model.addAttribute("bodyContent","categoryForm");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addCategory")
    public String getCategoryForm (@RequestParam String name,
                               @RequestParam String description){
        categoryService.create(name,description);
        return "redirect:/products";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manufacturerForm")
    public String getManufacturerForm (Model model){
        model.addAttribute("bodyContent","manufacturerForm");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addManufacturer")
    public String getManufacturerForm (@RequestParam String name,
                                   @RequestParam String address){
       manufacturerService.save(name,address);
        return "redirect:/products";
    }


}
