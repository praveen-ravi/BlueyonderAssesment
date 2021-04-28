package com.interview.blueyonder.services.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.blueyonder.resources.Customer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class CustomerDeserializer implements Deserializer<Customer> {

    @Override
    public Customer deserialize(String s, byte[] bytes) {
        ObjectMapper mapper =new ObjectMapper();
        Customer customer = null;
        try{
            customer = mapper.readValue(bytes,Customer.class);
        } catch (Exception e) {
            throw new SerializationException("Exception occurred while deserializing the Customer object from byte[]");
        }
        return customer;
    }
}
