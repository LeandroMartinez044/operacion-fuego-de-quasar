package com.martinez.operacionfuegodequasar.services.satellite;

import com.martinez.operacionfuegodequasar.dtos.response.PositionDTO;

public interface SatelliteService {

    PositionDTO getLocation(float... distances);

    String getMessage(String[]... messages);
}
