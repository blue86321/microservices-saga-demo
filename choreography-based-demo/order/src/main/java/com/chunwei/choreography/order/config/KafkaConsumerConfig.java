package com.chunwei.choreography.order.config;

import com.chunwei.choreography.common.dto.CheckInventoryEventResponseDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, CheckInventoryEventResponseDto>
    kafkaListenerContainerFactory(ConsumerFactory<Integer, CheckInventoryEventResponseDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, CheckInventoryEventResponseDto> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, CheckInventoryEventResponseDto> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerProps());
        return new DefaultKafkaConsumerFactory<>(
            consumerProps(),
            new IntegerDeserializer(),
            new JsonDeserializer<>(CheckInventoryEventResponseDto.class));
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }
}
