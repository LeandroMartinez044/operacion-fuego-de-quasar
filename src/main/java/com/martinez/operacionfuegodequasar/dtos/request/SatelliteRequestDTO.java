package com.martinez.operacionfuegodequasar.dtos.request;

import java.io.Serializable;
import java.util.List;

public class SatelliteRequestDTO implements Serializable {

    List<SatelliteDTO> satellites;

    public SatelliteRequestDTO() {
    }

    public List<SatelliteDTO> getSatellites() {
        return satellites;
    }

    public void setSatellites(List<SatelliteDTO> satellites) {
        this.satellites = satellites;
    }
}

