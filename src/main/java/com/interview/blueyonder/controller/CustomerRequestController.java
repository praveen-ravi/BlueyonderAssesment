package com.interview.blueyonder.controller;

import com.interview.blueyonder.resources.Customer;
import com.interview.blueyonder.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CustomerRequestController {
    @Autowired
    CustomerServices customerServices;
    @RequestMapping(method = RequestMethod.POST,value = "/customers")
    public ResponseEntity<String> pushCustomerRecord(@RequestBody Customer customer){
        ResponseEntity<String> response =null;
        try {
            String result = customerServices.pushCustomerRecord(customer);
            response= new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response= new ResponseEntity<>("Error occurred: "+e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }finally {
            return response;
        }
    }

    @RequestMapping("/startConsumer")
    public ResponseEntity<String> startConsumer(){
        ResponseEntity<String> response =null;
        try {
            customerServices.startConsumer();
            response = new ResponseEntity<>("Successfully started the consumer", HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<String> ("Failed Due to exception "+e, HttpStatus.EXPECTATION_FAILED);
        }finally {
            return response;
        }


    }

    @RequestMapping("/stopConsumer")
    public ResponseEntity<String> stopConsumer(){
        customerServices.setRunConsumer(false);
        return new ResponseEntity<>("Successfully stopped the consumer", HttpStatus.OK);
    }
}
