package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.dto.ProductDTO;
import com.ecommerce.productbecho.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class HomePageController {


    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        return "home-page";
    }

    @GetMapping("/getAllProducts")
    public String getAllProducts(Model model) {
        Set<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "view-products";
    }

    @GetMapping("/findProduct")
    public String findProduct(@RequestParam("key") String key, Model model) {
        Set<ProductDTO> products = productService.findByKey(key);
        model.addAttribute("products", products);
        return "search-result";
    }

}
