package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.entity.Cart;
import com.ecommerce.productbecho.facade.CartPopulator;
import com.ecommerce.productbecho.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CheckoutController {

    @Autowired
    private CartPopulator cartPopulator;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/checkout")
    public String add(Model model, HttpServletRequest httpServletRequest) throws Exception {
        if (readCookie("cartCode", httpServletRequest).isPresent()) {
            String cartId = readCookie("cartCode", httpServletRequest).get();
            Cart cart = cartRepository.findByCode(cartId);
            if (Objects.nonNull(cart.getAbstractOrder())) {
                model.addAttribute("cartdata", cartPopulator.populate(cart.getAbstractOrder()));
            } else throw new Exception();
            return "checkout";
        } else throw new Exception();
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
