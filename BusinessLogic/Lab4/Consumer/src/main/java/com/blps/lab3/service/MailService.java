package com.blps.lab3.service;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.OrdersJPA;
import com.blps.lab3.databaseJPA.Repositories.AccountsRepo;
import com.blps.lab3.databaseJPA.Repositories.OrdersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.mail.internet.MimeMessage;
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
