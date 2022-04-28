package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.facade.CartPopulator;
import com.ecommerce.productbecho.repository.AbstractOrderRepository;
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
    private AbstractOrderRepository abstractOrderRepository;

    @GetMapping("/cart")
    public String home(final Model model) {
        httpSession.setAttribute("abstractOrder", abstractOrderRepository.findAll().stream().findAny().orElse(null));
        AbstractOrder abstractOrder = (AbstractOrder) httpSession.getAttribute("abstractOrder");
        if (Objects.nonNull(abstractOrder)) {
            model.addAttribute("cartdata", cartPopulator.populate(abstractOrder));
        }
        return "cart-page";
    }

}

