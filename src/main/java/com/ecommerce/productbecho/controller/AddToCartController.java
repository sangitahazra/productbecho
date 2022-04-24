package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddToCartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity addProductToCart(@RequestParam("code") String code,
                                           @RequestParam("quantity") int quantity) throws Exception {
        cartService.addProductToCart(code, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity updateQuantity(@RequestParam("code") String code,
                                         @RequestParam("quantity") int quantity) throws Exception {
        cartService.updateQuantity(code, quantity);
        return ResponseEntity.ok().build();
    }

}
