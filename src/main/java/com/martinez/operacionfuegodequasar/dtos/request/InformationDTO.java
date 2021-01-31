package com.martinez.operacionfuegodequasar.dtos.request;

import java.io.Serializable;

public class InformationDTO implements Serializable {

    private float distance;

    private String[] message;


    public InformationDTO() {
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
