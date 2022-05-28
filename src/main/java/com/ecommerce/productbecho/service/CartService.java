package com.ecommerce.productbecho.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CartService {
    void addProductToCart(String code, int quantity, HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) throws Exception;

    void updateQuantity(String code, int quantity, HttpServletRequest httpServletRequest) throws Exception;

    void removeProduct(String code, HttpServletRequest httpServletRequest) throws Exception;
}
