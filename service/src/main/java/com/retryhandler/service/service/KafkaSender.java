package com.retryhandler.service.service;

import com.retryhandler.service.domain.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {
    private static final String TOPIC = "your-topic";

    @Autowired
    @Qualifier(value = "kafkaTemplate")
    private KafkaTemplate<String, CustomMessage> kafkaTemplate;

    public void sendMessage(CustomMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
