package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.Order;

public interface OrderService {
    Order placeOrder() throws Exception;
}
