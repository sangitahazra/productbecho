package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.Order;
import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.service.OrderService;
import com.ecommerce.productbecho.service.PBUserService;
import com.ecommerce.productbecho.service.ProductService;
import form.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BackofficeController {

    @Autowired
    ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PBUserService pbUserService;

    @GetMapping("/backoffice")
    public String home(Model model) {
        List<Order> orders = orderService.findAllOrders();
        List<PBUser> pbUsers = pbUserService.findAllUsers();
        if (!CollectionUtils.isEmpty(orders)){
            model.addAttribute("totalOrders", orders.size());
            model.addAttribute("revenue", orders.stream()
                    .map(Order::getAbstractOrder)
                    .mapToDouble(AbstractOrder::getTotalAmount)
                    .sum());
        }
        if(!CollectionUtils.isEmpty(pbUsers)) {
            model.addAttribute("users", pbUsers.size());
        }
        return "admin-home";
    }

    @GetMapping("/backoffice/get-product/")
    public String getProduct() {
        return "get-product";
    }

    @PostMapping("/backoffice/add-product")
    public String addProduct(@ModelAttribute("product") Product product,
                                     @RequestParam("imageFile") MultipartFile file) throws IOException {
        productService.uploadProduct(product, file);
        return "product-success";
    }
}
