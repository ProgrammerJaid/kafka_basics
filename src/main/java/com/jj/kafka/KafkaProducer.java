package com.jj.kafka;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaProducer {
	
	private static final List<String> RESPONSES = Arrays.asList(
		    "Thank you for your feedback!",
		    "We appreciate your input.",
		    "Your feedback is valuable to us.",
		    "Thanks for letting us know!",
		    "We will look into this.",
		    "Thank you for your suggestion!",
		    "We are glad to hear from you.",
		    "Your feedback helps us improve.",
		    "Thanks for sharing your thoughts.",
		    "We appreciate your comments."
		);
	
	private String[] productNames = {"Product A", "Product B", "Product C", "Product D", "Product E", 
            "Product F", "Product G", "Product H", "Product I", "Product J"};
	
	@Autowired
	private KafkaTemplate<String, Product> kafkaTemplate;

//	@Scheduled(fixedRate = 2000)
//	void produceSentence() {
//		String response = RESPONSES.get(new Random().nextInt(RESPONSES.size()));
//		CompletableFuture<SendResult<String, String>> sentEvent = kafkaTemplate.send("responses-3", response.substring(0,2),response);
//		sentEvent.whenComplete((rs,ex)->{
//			if(ex==null) {
//				log.info("Customer feedback sent-> "+sentEvent+" , with offset-> "+rs.getRecordMetadata().offset());
//			}else {
//				log.error("Unable to sent the customer feedback-> "+sentEvent+" reason "+ex.getMessage());
//				// add it to DLQ
//			}
//		});
//	}
	
	@Scheduled(fixedRate = 2000)
	void produceProduct() {
		Product product = new Product(productNames[new Random().nextInt(productNames.length-1)],new Random().nextDouble(10,99));
		CompletableFuture<SendResult<String, Product>> sendEvent = kafkaTemplate.send("product-value-6",product.getName(),product);
		sendEvent.whenComplete((rs,ex)->{
			if(ex==null) {
				log.info("Customer feedback sent-> "+sendEvent+" , with offset-> "+rs.getRecordMetadata().offset());
			}else {
				log.error("Unable to sent the customer feedback-> "+sendEvent+" reason "+ex.getMessage());
				// add it to DLQ
			}
		});
	}
	
}
