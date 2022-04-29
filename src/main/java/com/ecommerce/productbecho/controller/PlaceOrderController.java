package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceOrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/placeOrder")
    public String placeOrder() throws Exception {
        orderService.placeOrder();
        return "order-success";
    }

}
