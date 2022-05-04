package com.ecommerce.productbecho.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnOrderConfirmationEvent extends ApplicationEvent {

    private String email;
    private String orderCode;
    private String name;

    public OnOrderConfirmationEvent(String userName, String orderCode, String name) {
        super(userName);
        this.email = userName;
        this.orderCode = orderCode;
        this.name = name;
    }

}
