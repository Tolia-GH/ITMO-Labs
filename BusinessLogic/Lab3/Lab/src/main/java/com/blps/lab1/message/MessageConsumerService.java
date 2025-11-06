package com.blps.lab1.message;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String CLIENT_ID = "consumer-client";

    public void startListening(String topic) throws MqttException {
        MqttClient mqttClient = new MqttClient(BROKER_URL, CLIENT_ID);
        mqttClient.setCallback(new MqttCallback() {
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost" + cause.getMessage());
            }

            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("Message arrived from topic" + topic + ": " + message.toString());
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery complete");
            }
        });

        mqttClient.connect();
        mqttClient.subscribe(topic);
    }

}
