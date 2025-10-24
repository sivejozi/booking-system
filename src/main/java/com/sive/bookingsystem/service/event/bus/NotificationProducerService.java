package com.sive.bookingsystem.service.event.bus;

import com.sive.bookingsystem.exception.message.MessageNotFoundException;
import com.sive.bookingsystem.service.event.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducerService.class);

    @Autowired
    public NotificationProducerService(@Qualifier("kafkaTemplate") KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessage(Message message) {
        if (message == null) {
            throw new MessageNotFoundException();
        }
        kafkaTemplate.send("notification-topic", message.getKey(), message);
        logger.info("notification message sent to be published");
    }
}
