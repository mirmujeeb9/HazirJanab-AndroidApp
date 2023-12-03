package com.faizanahmed.janabhazir;

public class NormalBooking {
    private int id;
    private int userId;
    private int serviceId;
    private int vendorId;
    private String city;
    private String address;
    private String date;
    private String time;
    private String description;
    private byte[] picture;


    public NormalBooking(int userId, int serviceId, int vendorId, String city, String address, String date, String time, String description, byte[] picture) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.vendorId = vendorId;
        this.city = city;
        this.address = address;
        this.date = date;
        this.time = time;
        this.description = description;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}

