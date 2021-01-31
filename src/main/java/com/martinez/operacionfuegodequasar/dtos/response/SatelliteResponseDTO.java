package com.martinez.operacionfuegodequasar.dtos.response;

import java.io.Serializable;

public class SatelliteResponseDTO implements Serializable {

    private PositionDTO position;

    private String message;

    public SatelliteResponseDTO(PositionDTO position, String message) {
        this.position = position;
        this.message = message;
    }

    public PositionDTO getPosition() {
        return position;
    }

    public String getMessage() {
        return message;
    }
}
