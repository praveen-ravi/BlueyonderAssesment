package com.interview.blueyonder.resources;

import javax.annotation.Generated;
import java.util.UUID;

public class Customer {


    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;

    public Customer(){
        this.id = UUID.randomUUID().toString();
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return(String.format("Customer(%s ,%s, %s, %s)",id,firstName,lastName,emailAddress));
    }
}
