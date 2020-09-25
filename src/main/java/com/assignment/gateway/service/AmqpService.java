package com.assignment.gateway.service;

import com.assignment.gateway.model.FileType;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AmqpService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Qualifier("xml")
    @Autowired
    Queue xmlQueue;

    @Qualifier("csv")
    @Autowired
    Queue csvQueue;

    public void sendToQueue(FileType fileType, byte[] encryptedMessage){
        String queueName;
        switch (fileType){
            case CSV:
                queueName = csvQueue.getName();
                break;
            case XML:
                queueName = xmlQueue.getName();
                break;
            default:
                throw new RuntimeException("Unsupported file type");
        }
        rabbitTemplate.convertAndSend(queueName, encryptedMessage);
    }
}
