package com.freesundance.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue inboundQueue() {
        return new ActiveMQQueue("queue.inbound");
    }

    @Bean
    public Queue leftQueue() {
        return new ActiveMQQueue("queue.left");
    }

    @Bean
    public Queue rightQueue() {
        return new ActiveMQQueue("queue.right");
    }
}
