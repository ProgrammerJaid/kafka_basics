package com.jj.kafka;

import java.util.HashSet;
import java.util.Set;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsMetadata;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class QueryReaderController {

	@Autowired
	private KafkaStreams kafkaStreams;
	
	@GetMapping("/products/{key}")
	public Product getProduct(@PathVariable String key) {
		log.info("invoking ");
		ReadOnlyKeyValueStore<String, Product> keyValueStore =
		        kafkaStreams.store(StoreQueryParameters.fromNameAndType("product-store-6", QueryableStoreTypes.keyValueStore()));
		return keyValueStore.get(key);
	}
	
	@GetMapping("/list-stores")
    public Set<String> listStores() {
        Set<String> storeNames = new HashSet<>();
        for (StreamsMetadata metadata : kafkaStreams.metadataForAllStreamsClients()) {
            storeNames.addAll(metadata.stateStoreNames());
        }
        return storeNames;
    }
	
}
