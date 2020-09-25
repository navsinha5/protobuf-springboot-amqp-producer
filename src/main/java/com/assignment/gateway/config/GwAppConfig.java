package com.assignment.gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GwAppConfig {
    @Value("${gw.service.amqp.queue.xml}")
    private String xmlQueue;

    @Value("${gw.service.amqp.queue.csv}")
    private String csvQueue;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean("xml")
    public Queue personQueueXml(){
        return new Queue(xmlQueue);
    }

    @Bean("csv")
    public Queue personQueueCsv(){
        return new Queue(csvQueue);
    }

}
