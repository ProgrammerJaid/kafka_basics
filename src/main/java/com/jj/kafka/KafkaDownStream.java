package com.jj.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import jakarta.annotation.PostConstruct;

@Configuration
public class KafkaDownStream {

	@Autowired
	private StreamsBuilder streamsBuilder;
	
	@PostConstruct
	void consumeEvent() {
		//uncomement below two line to print data
//		KStream<String, String> customerFeedBackStream = streamsBuilder.stream("responses-3",Consumed.with(Serdes.String(),Serdes.String()));
//		customerFeedBackStream.print(Printed.<String, String>toSysOut().withLabel("Reponses"));
		
		//creating table with the topic of responses-3
//		KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore("responses-store-1");
//
//		streamsBuilder.table(
//		    "responses-3",
//		    Materialized.<String, String>as(storeSupplier)
//		        .withKeySerde(Serdes.String())
//		        .withValueSerde(Serdes.String())
//		);
		
		//this creates KTable with internal state store which is not querable
//		streamsBuilder.table("product-value-1",Consumed.with(Serdes.String(), new JsonSerde<>(Product.class)));
		
		//printing the stream 
//		streamsBuilder.stream("product-value-5",Consumed.with(Serdes.String(), new JsonSerde<>(Product.class)))
//		.print(Printed.<String, Product>toSysOut().withLabel("Product Detail"));
		
		//using kafka stores
		KeyValueBytesStoreSupplier productStore = Stores.inMemoryKeyValueStore("product-store-6");
//		StoreBuilder<KeyValueStore<String, Product>> keyValueStoreBuilder = Stores.keyValueStoreBuilder(productStore, Serdes.String(),new JsonSerde<>(Product.class));
//		streamsBuilder.addStateStore(keyValueStoreBuilder);
		streamsBuilder.table("product-value-6",  Consumed.with(Serdes.String(), new JsonSerde<>(Product.class)),Materialized.<String, Product>as(productStore));
	}
	
}
