package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.Cart;
import com.ecommerce.productbecho.facade.CartPopulator;
import com.ecommerce.productbecho.repository.AbstractOrderRepository;
import com.ecommerce.productbecho.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class CartPageController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CartPopulator cartPopulator;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart")
    public String cart(final Model model) {
        String cartId = (String) httpSession.getAttribute("cartID");
        Cart cart = cartRepository.findByCode(cartId);
        if (Objects.nonNull(cart.getAbstractOrder())) {
            model.addAttribute("cartdata", cartPopulator.populate(cart.getAbstractOrder()));
        }
        return "cart";
    }

}

