package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.event.OnOrderConfirmationEvent;
import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;
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

import javax.servlet.http.HttpSession;
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

    @ResponseBody
    @PostMapping("/checkout/guest/add/")
    public ResponseEntity add(@RequestBody GuestUserData guestUserData) throws Exception {
        Optional<PBUser> pbUser = pbUserService.getUser(guestUserData);
        PBUser pbUserEntity;
        if (pbUser.isEmpty()) {
            pbUserEntity = pbUserService.addUser(guestUserData);
        } else {
            pbUserEntity = pbUser.get();
        }
        httpSession.setAttribute("user", pbUserEntity);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping("/checkout/address/add/")
    public ResponseEntity saveAddress(@RequestBody AddressData addressData) throws Exception {
        pbUserService.addAddress(addressData);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping("/checkout/payment/add/")
    public ResponseEntity placeOrder() throws Exception {
        orderService.placeOrder();
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
}