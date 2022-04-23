package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.dto.ProductDTO;
import com.ecommerce.productbecho.service.ProductService;
import form.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BackofficeController {

    @Autowired
    ProductService productService;

    @GetMapping("/backoffice")
    public String home(Model model) {
        model.addAttribute("product", new Product());
        return "admin-home";
    }

    @PostMapping("/backoffice/add-product")
    public String addProduct(@ModelAttribute("product") Product product, Model model,
                                 @RequestParam("imageFile") MultipartFile file) throws IOException {
        productService.uploadProduct(product, file);
        model.addAttribute("uploaded", true);
        return "admin-home";
    }

    @GetMapping("/getAllProducts")
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "view-products";
    }
}
