package com.martinez.operacionfuegodequasar.dtos.request;


import java.io.Serializable;

public class SatelliteDTO implements Serializable {

    private String name;

    private float distance;

    private String[] message;


    public SatelliteDTO() {
    }

    public SatelliteDTO(String name, float distance, String[] message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
