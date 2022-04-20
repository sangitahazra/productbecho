package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddToCartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    public void addProductToCart(@RequestParam("code") String code, @RequestParam("quantity") int quantity) {
        cartService.addProductToCart(code,quantity);
    }

}
