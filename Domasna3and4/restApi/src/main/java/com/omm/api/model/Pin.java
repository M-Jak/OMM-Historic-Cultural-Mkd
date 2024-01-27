package com.omm.api.model;


import lombok.Data;

@Data
public class Pin {

    private String name;
    private String type;
    private String en_name;
    private double latitude;
    private double longitude;

    public Pin(String name, String type,String en_name, double latitude, double longitude) {
        this.name = name;
        this.en_name = en_name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
