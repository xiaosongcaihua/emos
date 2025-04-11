package com.emos.wx.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${db.host}")
    public String host;
    @Bean
    public ConnectionFactory getFactory(){
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(5672);
        return factory;
    }
}
