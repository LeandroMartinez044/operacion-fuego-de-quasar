package com.martinez.operacionfuegodequasar.dtos.response;

public class PositionDTO {

    private float x;

    private float y;

    public PositionDTO(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
