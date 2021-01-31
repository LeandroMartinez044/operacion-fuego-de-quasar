package com.martinez.operacionfuegodequasar.services.topsecret;


import com.martinez.operacionfuegodequasar.dtos.response.SatelliteResponseDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteRequestDTO;

public interface TopSecretService {

    SatelliteResponseDTO processInformationFromSatellites(SatelliteRequestDTO satelliteRequestDTO);

}
