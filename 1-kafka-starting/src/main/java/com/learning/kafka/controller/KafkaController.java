package com.learning.kafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.kafka.model.Employee;
import com.learning.kafka.util.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/sendToTopic")
    public String sendToTopic(@RequestBody Employee employee) throws JsonProcessingException {
        kafkaTemplate.send(Constants.TOPIC_NAME, objectMapper.writeValueAsString(employee)).addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("error sending to kafka", ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("send result {}", result);
            }
        });
        return "done";
    }

    @PostMapping("/sendToTopicPartition")
    public String sendToTopicPartition(@RequestBody Employee employee) throws JsonProcessingException {
        kafkaTemplate.send(Constants.TOPIC_NAME, employee.getId(), objectMapper.writeValueAsString(employee)).addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("error sending to kafka", ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("send result {}", result);
            }
        });
        return "done";
    }
}
