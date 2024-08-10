package com.jj.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class KafkaConfig {
	
	@Bean
	NewTopic customerFeedBackTopic() {
		return new NewTopic("product-value-6", 1,(short)1);
	}
	
	@Bean
	ProducerFactory<String, Product> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	KafkaTemplate<String, Product> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
    KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder) {
		 final Topology topology = streamsBuilder.build();
	        KafkaStreams streams = new KafkaStreams(topology, defaultKafkaStreamsConfig().asProperties());
	        
	        // Start the streams in a separate thread
	        new Thread(() -> {
	            streams.start();
	        }).start();
	        
	        // Add a shutdown hook
	        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	        
	        return streams;
    }
	
	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration defaultKafkaStreamsConfig() {
        // Your Kafka Streams configuration here
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "prooduct-cnm-6");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Add other necessary configuration
        return new KafkaStreamsConfiguration(props);
    }
	
//	@Bean
//	ApplicationListener<ApplicationReadyEvent> readinessCheck(KafkaStreams kafkaStreams) {
//	    return event -> {
//	        while (kafkaStreams.state() != KafkaStreams.State.RUNNING) {
//	            try {
//	                Thread.sleep(1000);
//	            } catch (InterruptedException e) {
//	                Thread.currentThread().interrupt();
//	            }
//	        }
//	        System.out.println("KafkaStreams is now in RUNNING state");
//	    };
//	}
	
}
