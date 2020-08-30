package com.example.feedmeappjava.Model;

public class UserAddress {

    private String label;
    private String Address;

    public UserAddress() {
    }

    public UserAddress(String label, String address) {
        this.label = label;
        Address = address;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}

