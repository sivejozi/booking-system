package com.sive.bookingsystem.service.event.bus;

import com.sive.bookingsystem.service.event.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumerService.class);

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consume(Message message) {
        logger.info("Received message: {}", message);
        issueNotification(message);
    }

    private void issueNotification(Message message) {
        logger.info("Notification issued to customer: {} | Content: {}",
                message.getData().getAppointment().getCustomerEmail(), message.getDescription());

        //Now here!!!
    }
}

