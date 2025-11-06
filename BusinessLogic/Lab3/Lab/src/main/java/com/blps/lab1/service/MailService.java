package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import com.blps.lab1.databaseJPA.Repositories.AccountsRepo;
import com.blps.lab1.databaseJPA.Repositories.OrdersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private AccountsRepo accountsRepo;

//    @JmsListener(destination = "Consumer/mail/VirtualTopic/order/payment")
//    public void onMessage(byte[] messageBytes) {
//
//        try {
//            String message = new String(messageBytes, StandardCharsets.UTF_8);
//            System.out.println("Message received by JmsListener: " + message);
//            Map<String, Object> orderInfo = objectMapper.readValue(message, Map.class);
//
//            String userEmail = (String) orderInfo.get("userEmail");
//            Integer orderId = (Integer) orderInfo.get("orderId");
//
//            if (ordersRepo.findById(orderId).isPresent()) {
//                OrdersJPA order = ordersRepo.findById(orderId).get();
//                if (accountsRepo.findByEmail(userEmail).isPresent()) {
//                    AccountsJPA account = accountsRepo.findByEmail(userEmail).get();
//
//                    sendMail("Order Confirmation", order, account);
//                    System.out.println("Start sending confirmation email");
//                } else {
//                    System.out.println("Order not found");
//                }
//            } else {
//                System.out.println("Order not found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void sendMail(String subject, OrdersJPA order, AccountsJPA account) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("customerName", account.getUsername().toString());
            model.put("orderId", order.getId().toString());
            String htmlContent = thymeleafViewResolver.getTemplateEngine().process(
                    "orderConfirmation", new org.thymeleaf.context.Context(null, model));

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setBcc(account.getEmail().toString());
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("2398768715@qq.com");

            mailSender.send(mimeMessage);
            System.out.println("HTML email sent successfully!");
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
