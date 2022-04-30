package com.ecommerce.productbecho.controller;

import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;
import com.ecommerce.productbecho.service.OrderService;
import com.ecommerce.productbecho.service.PBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestCheckoutController {

    @Autowired
    private PBUserService pbUserService;

    @Autowired
    OrderService orderService;


    @PostMapping("/checkout/guest/add/")
    public ResponseEntity add(@RequestBody GuestUserData guestUserData) throws Exception {
        if (pbUserService.getUser(guestUserData).isEmpty()) {
            pbUserService.addUser(guestUserData);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout/address/add/")
    public ResponseEntity saveAddress(@RequestBody AddressData addressData) throws Exception {
        pbUserService.addAddress(addressData);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout/payment/add/")
    public ResponseEntity placeOrder() throws Exception {
        orderService.placeOrder();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkout/success")
    public String orderSuccess() {
        return "order-success";
    }
}
