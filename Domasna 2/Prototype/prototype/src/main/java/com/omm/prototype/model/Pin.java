package com.omm.prototype.model;


import lombok.Data;

@Data
public class Pin {

    private String name;
    private String type;
    private double latitude;
    private double longitude;

    public Pin(String name, String type, double latitude, double longitude) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
