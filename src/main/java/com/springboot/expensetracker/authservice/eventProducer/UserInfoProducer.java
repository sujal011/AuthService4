package com.springboot.expensetracker.authservice.eventProducer;

import com.springboot.expensetracker.authservice.models.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {

    private final KafkaTemplate<String, UserInfoDTO> kafkaTemplate;

//    @Value("$spring.kafka.topic.name")
    private final String TOPIC_NAME = "testing_json";

    @Autowired
    public UserInfoProducer(KafkaTemplate<String, UserInfoDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventToKafka(UserInfoDTO userInfoDTO){
        Message<UserInfoDTO> message = MessageBuilder.withPayload(userInfoDTO)
                .setHeader(KafkaHeaders.TOPIC,TOPIC_NAME)
                .build();
        kafkaTemplate.send(message);
    }
}
