package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.event.OnOrderConfirmationEvent;
import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;
import com.ecommerce.productbecho.repository.AbstractOrderRepository;
import com.ecommerce.productbecho.service.OrderService;
import com.ecommerce.productbecho.service.PBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class GuestCheckoutController {

    @Autowired
    private PBUserService pbUserService;

    @Autowired
    OrderService orderService;

    @Autowired
    HttpSession httpSession;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;


    @Autowired
    private AbstractOrderRepository abstractOrderRepository;

    @ResponseBody
    @PostMapping("/checkout/guest/add/")
    public ResponseEntity add(@RequestBody GuestUserData guestUserData, HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse) throws Exception {
        Optional<PBUser> pbUser = pbUserService.getUser(guestUserData);
        PBUser pbUserEntity;
        if (pbUser.isEmpty()) {
            pbUserEntity = pbUserService.addUser(guestUserData, httpServletRequest, httpServletResponse);
        } else {
            pbUserEntity = pbUser.get();
        }
        AbstractOrder abstractOrder = getAbstractOrderFromCookie(httpServletRequest);
        if (null != abstractOrder) {
            abstractOrder.setPbUser(pbUserEntity);
            AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
            setCookie("abstractOrder", String.valueOf(abstractOrderEntity.getPk()), httpServletResponse);
        }
        httpSession.setAttribute("user", pbUserEntity);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping("/checkout/address/add/")
    public ResponseEntity saveAddress(@RequestBody AddressData addressData,
                                      HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {
        pbUserService.addAddress(addressData, httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping("/checkout/payment/add/")
    public ResponseEntity placeOrder(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        orderService.placeOrder(httpServletRequest);
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("cartCode") || cookies[i].getName().equals("abstractOrder")) {
                    cookies[i].setMaxAge(0);
                    httpServletResponse.addCookie(cookies[i]);
                }
            }
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/checkout/success")
    public String orderSuccess(Model model) {
        String orderCode = (String) httpSession.getAttribute("orderCode");
        model.addAttribute("orderCode", orderCode);
        PBUser pbUser = (PBUser) httpSession.getAttribute("user");
        eventPublisher.publishEvent(new OnOrderConfirmationEvent(pbUser.getUserName(), orderCode
                , pbUser.getName()));
        return "order-success";
    }


    public AbstractOrder getAbstractOrderFromCookie(HttpServletRequest httpServletRequest) {
        String abstractOrderPk = readCookie("abstractOrder", httpServletRequest).get();
        Optional<AbstractOrder> abstractOrderOptional = abstractOrderRepository.findById(Integer.valueOf(abstractOrderPk));
        return abstractOrderOptional.orElse(null);
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