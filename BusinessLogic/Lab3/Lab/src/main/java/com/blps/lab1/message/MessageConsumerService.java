package com.blps.lab1.message;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import com.blps.lab1.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class MessageConsumerService {
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String CLIENT_ID = "consumer-client";
    private static final String TOPIC_NAME = "Consumer.mail.VirtualTopic.order.payment";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MailService mailService;

    @PostConstruct
    public void init() throws MqttException {
        MqttClient mqttClient = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setAutomaticReconnect(true); // 自动重连
        connOpts.setCleanSession(false); // 保留订阅
        connOpts.setConnectionTimeout(10);

        mqttClient.connect(connOpts);

        mqttClient.subscribe(TOPIC_NAME, 1, (topic, msg) -> {
            String json = new String(msg.getPayload(), StandardCharsets.UTF_8);
            System.out.println("Received MQTT message: " + json);

            try {
                // 解析 JSON
                Map<String, Object> payload = mapper.readValue(json, Map.class);
                Integer orderId = (Integer) payload.get("orderId");
                String userEmail = (String) payload.get("userEmail");
                String username = (String) payload.get("username");

                // 构建临时订单/账户对象用于传参
                OrdersJPA order = new OrdersJPA();
                order.setId(orderId);

                AccountsJPA account = new AccountsJPA();
                account.setEmail(userEmail);
                account.setUsername(username);

                mailService.sendMail("Order Confirmation", order, account);
            } catch (Exception e) {
                System.out.println("Error processing MQTT message: " + e.getMessage());
            }
        });
    }
}
