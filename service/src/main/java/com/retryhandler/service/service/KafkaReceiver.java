package com.retryhandler.service.service;

import com.retryhandler.service.exception.CustomRetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.RetryTopicHeaders;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KafkaReceiver {

    private int attemptCount = 0;



    @RetryableTopic(attempts = "4",
            backoff = @Backoff(delay = 30000, multiplier = 2),
            include = {CustomRetryableException.class},
            dltStrategy = DltStrategy.NO_DLT,
            autoCreateTopics = "false", kafkaTemplate ="kafkaTemplate")
    @KafkaListener(topics = "your-topic",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message,
                       @Header(name = RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS) Integer nonBlockingAttempts,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
            throws CustomRetryableException {
        log.info("Message received from topic: {}", topic);
        log.info("Non Blocking Attempt no: {}", nonBlockingAttempts);
        log.info("Received message: " + message);
        if (attemptCount < 4) {
            attemptCount++;

            throw new CustomRetryableException("Simulated exception for retry");
        }
        log.info("Message processed successfully on attempt: {}", attemptCount);
    }


}

