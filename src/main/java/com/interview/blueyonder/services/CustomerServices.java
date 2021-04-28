package com.interview.blueyonder.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.blueyonder.resources.Customer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Future;

@Service
public class CustomerServices {

    private Properties kafkaProperties;
    private Properties projectProperties;
    private boolean runConsumer = false;

    public boolean isRunConsumer() {
        return runConsumer;
    }

    public void setRunConsumer(boolean runConsumer) {
        this.runConsumer = runConsumer;
    }

    public CustomerServices() throws IOException {
        kafkaProperties= new Properties();
        kafkaProperties.load(getClass().getClassLoader().getResourceAsStream("kafka.properties"));
        projectProperties= new Properties();
        projectProperties.load(getClass().getClassLoader().getResourceAsStream("project.properties"));
    }
    public String pushCustomerRecord(Customer customer) throws IOException {
        Producer<String,Customer> producer = getKafkaProducer();
        Future<RecordMetadata> result=producer.send(new ProducerRecord<String,Customer>(kafkaProperties.getProperty("topicName"),customer.getId(),customer));
        producer.close();
        try {
            result.get();
            return "Success";
        }catch (Exception e){
            return "Failed";
        }
    }

    public Producer<String,Customer> getKafkaProducer() {
        return new KafkaProducer<String, Customer>(kafkaProperties);
    }

    public Consumer<String,Customer> getKafkaConsumer(){
        return new KafkaConsumer<String, Customer>(kafkaProperties);
    }

    public void startConsumer(){
        if(!runConsumer) {
            runConsumer = true;
            Consumer<String, Customer> kafkaConsumer = getKafkaConsumer();
            kafkaConsumer.subscribe(Arrays.asList(kafkaProperties.get("topicName").toString()));
            try {
                startConsumerThread(() -> {
                    while (runConsumer)
                        kafkaConsumer.poll(Duration.ofMillis(Long.parseLong(projectProperties.getProperty("consumer.poll.duration")))).forEach((rec) -> writeToFile(rec));
                    kafkaConsumer.close();
                });
            } catch (Exception e) {
                throw e;
            }
        }else{
            return;
        }
    }

    private void startConsumerThread(Runnable runnable){
        Thread consumerThread =new Thread(runnable);
        consumerThread.start();
    }

    private void writeToFile(ConsumerRecord<String,Customer> record){
        File destinationFile = new File(projectProperties.getProperty("consumer.output"));
        try {
            if (!destinationFile.isFile()) {
                destinationFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(projectProperties.getProperty("consumer.output"));
            ObjectMapper mapper = new ObjectMapper();
            fileWriter.append(mapper.writeValueAsString(record.value()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
