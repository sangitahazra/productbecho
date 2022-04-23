package com.ecommerce.productbecho.service;

public interface CartService {
    void addProductToCart(String code, int quantity) throws Exception;
}
