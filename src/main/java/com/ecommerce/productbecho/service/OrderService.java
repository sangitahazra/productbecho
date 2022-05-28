package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    Order placeOrder(HttpServletRequest httpServletRequest) throws Exception;

    List<Order> findAllOrders();
}
