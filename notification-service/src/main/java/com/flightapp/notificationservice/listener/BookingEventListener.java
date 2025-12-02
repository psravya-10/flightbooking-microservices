package com.flightapp.notificationservice.listener;

import com.flightapp.notificationservice.config.RabbitConfig;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BookingEventListener {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handle(Map<String, Object> data) throws MessagingException {

        String email = data.get("email").toString();
        String pnr = data.get("pnr").toString();
        String status = data.get("status").toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Flight Booking Update: " + status);
        message.setText("Your booking with PNR: " + pnr + " is " + status);

        mailSender.send(message);

        System.out.println("Email sent to: " + email);
    }
}
