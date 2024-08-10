package com.jj.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@EnableKafka
@RestController
@EnableScheduling
@EnableKafkaStreams
@SpringBootApplication
public class KafkaBasicsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaBasicsApplication.class, args);
	}
}
