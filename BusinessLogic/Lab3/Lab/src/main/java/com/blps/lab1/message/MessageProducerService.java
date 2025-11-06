package com.blps.lab1.message;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageProducerService {
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String CLIENT_ID = "producer-client";

    private final ObjectMapper mapper = new ObjectMapper();

    public void sendPaymentMessage(OrdersJPA order, AccountsJPA account) {
        try {
            MqttClient mqttClient = new MqttClient(BROKER_URL, CLIENT_ID);
            mqttClient.connect();

            Map<String, Object> payload = new HashMap<>();
            payload.put("orderId", order.getId());
            payload.put("userEmail", account.getEmail());
            payload.put("username", account.getUsername());

            String jsonMessage = mapper.writeValueAsString(payload);

            MqttMessage mqttMessage = new MqttMessage(jsonMessage.getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(1);
            mqttClient.publish("Consumer.mail.VirtualTopic.order.payment", mqttMessage);

            System.out.println("Message sent via MQTT");
            mqttClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
