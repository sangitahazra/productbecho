package com.ecommerce.productbecho.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class OrderConfirmationListener implements ApplicationListener<OnOrderConfirmationEvent> {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    HttpSession httpSession;

    @Override
    public void onApplicationEvent(OnOrderConfirmationEvent event) {
        this.confirmOrder(event);
    }

    private void confirmOrder(OnOrderConfirmationEvent event) {
        String recipientAddress = event.getEmail();
        String subject = "Your ProductBecho Order : " + event.getOrderCode();
        String message = "Hi " + event.getName() + ", Thank you for shopping with us. You have placed your order successfully! ";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("productbecho@gmail.com");
        mailSender.send(mailMessage);
    }
}
