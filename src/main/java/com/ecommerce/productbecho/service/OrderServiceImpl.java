package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.Order;
import com.ecommerce.productbecho.repository.AbstractOrderRepository;
import com.ecommerce.productbecho.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    HttpSession httpSession;

    @Autowired
    AbstractOrderRepository abstractOrderRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order placeOrder() throws Exception {
        AbstractOrder abstractOrder = (AbstractOrder) httpSession.getAttribute("abstractOrder");
        if(null != abstractOrder) {
            Order order = new Order();
            order.setAbstractOrder(abstractOrder);
            order.setCode(UUID.randomUUID().toString());
            return orderRepository.save(order);
        }
        else throw new Exception();
    }
}
