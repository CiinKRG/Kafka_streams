package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.state.KeyValueStore;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class stream {
    public static void main(String[] args) {

       Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "word_count_application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        // Stream from Kafka
        KStream<String,String> wordCountInput =  builder.stream("word_count_input");

        KTable<String,Long> wordCounts = wordCountInput
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+"))) // Flatmap values split by space
                .groupBy((key, word) -> word) // Group by key before aggregation
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store")); // count occurrences

        wordCounts.toStream().to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
        //printed the topology
        System.out.println(streams.toString());

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
