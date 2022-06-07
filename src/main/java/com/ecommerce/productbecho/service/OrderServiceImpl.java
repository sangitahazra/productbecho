package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.Order;
import com.ecommerce.productbecho.repository.AbstractOrderRepository;
import com.ecommerce.productbecho.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepository orderRepository;


    @Autowired
    AbstractOrderRepository abstractOrderRepository;

    @Autowired
    HttpSession httpSession;


    @Override
    public Order placeOrder(HttpServletRequest httpServletRequest) throws Exception {
        String abstractOrderPk = readCookie("abstractOrder", httpServletRequest).get();
        Optional<AbstractOrder> abstractOrderOptional = abstractOrderRepository.findById(Integer.valueOf(abstractOrderPk));
        if (abstractOrderOptional.isPresent()) {
            AbstractOrder abstractOrder = abstractOrderOptional.get();
            Order order = new Order();
            order.setAbstractOrder(abstractOrder);
            order.setCode(UUID.randomUUID().toString());
            Order orderEntity = orderRepository.save(order);
            httpSession.setAttribute("orderCode", orderEntity.getCode());
            return orderEntity;
        } else throw new Exception();
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<String> readCookie(String key, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() != null) {
            return Arrays.stream(httpServletRequest.getCookies())
                    .filter(c -> key.equals(c.getName()))
                    .map(Cookie::getValue)
                    .findAny();
        }
        return Optional.empty();
    }

    public void setCookie(String name, String value, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
