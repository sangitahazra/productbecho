package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder() throws Exception;

    List<Order> findAllOrders();
}
