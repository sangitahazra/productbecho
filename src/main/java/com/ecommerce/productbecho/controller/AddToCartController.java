package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AddToCartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity addProductToCart(@RequestParam("code") String code,
                                           @RequestParam("quantity") int quantity, HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse) throws Exception {
        cartService.addProductToCart(code, quantity, httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity updateQuantity(@RequestParam("code") String code,
                                         @RequestParam("quantity") int quantity, HttpServletRequest httpServletRequest) throws Exception {
        cartService.updateQuantity(code, quantity, httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeProduct")
    public ResponseEntity removeProduct(@RequestParam("code") String code, HttpServletRequest httpServletRequest) throws Exception {
        cartService.removeProduct(code, httpServletRequest);
        return ResponseEntity.ok().build();
    }

}
