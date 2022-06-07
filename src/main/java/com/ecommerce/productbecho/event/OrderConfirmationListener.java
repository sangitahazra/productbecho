package com.ecommerce.productbecho.event;

import com.ecommerce.productbecho.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmationListener implements ApplicationListener<OnOrderConfirmationEvent> {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProductService productService;

    @Override
    public void onApplicationEvent(OnOrderConfirmationEvent event) {
        this.confirmOrder(event);
    }

    private void confirmOrder(OnOrderConfirmationEvent event) {
        String recipientAddress = event.getEmail();
        String subject = "Your ProductBecho Order : " + event.getOrderCode();
        String message = "Hi " + event.getName() + ", Thank you for shopping with us. You have placed your order successfully! ";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("do-not-reply-productbecho@gmail.com");
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
        SimpleMailMessage mailMessageToSeller = new SimpleMailMessage();
        mailMessageToSeller.setFrom("do-not-reply-productbecho@gmail.com");
        mailMessageToSeller.setTo("productbecho@gmail.com");
        mailMessageToSeller.setSubject("Order was placed with id : " + event.getOrderCode());
        mailMessageToSeller.setText("The order details to be fulfilled : \n \n" +
                productService.populateOrderDetails());
        mailSender.send(mailMessageToSeller);
    }
}
