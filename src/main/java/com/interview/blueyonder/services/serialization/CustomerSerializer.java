package com.interview.blueyonder.services.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.blueyonder.resources.Customer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class CustomerSerializer implements Serializer<Customer> {

    @Override
    public byte[] serialize(String s, Customer customer) {
        byte[] customerSerialized = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            customerSerialized = objectMapper.writeValueAsString(customer).getBytes();
        }catch(JsonProcessingException e){
            throw new SerializationException("Error while serializing customer object to bytes[]");
        }
        return customerSerialized;
    }
}
