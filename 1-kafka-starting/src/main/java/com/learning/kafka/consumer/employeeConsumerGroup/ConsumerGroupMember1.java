package com.learning.kafka.consumer.employeeConsumerGroup;

import com.learning.kafka.util.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerGroupMember1 {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.CONSUMER_GROUP)
    public void listen(String employee,
                       @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) Integer key,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        logger.info("consuming from ConsumerGroupMember1 {} key-> {} partitionNumber-> {} topicName-> {} timeStamp-> {}", employee, key, partition, topic, ts);
    }
}
