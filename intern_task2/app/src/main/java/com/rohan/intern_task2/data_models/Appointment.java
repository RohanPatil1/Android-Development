package com.rohan.intern_task2.data_models;

public class Appointment {

    String customer;
    String name ;
    String  phone_number;

    long timestamp;


    public Appointment(String customer, String name, String phone_number, long timestamp) {
        this.customer = customer;
        this.name = name;
        this.phone_number = phone_number;

        this.timestamp = timestamp;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }



    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
