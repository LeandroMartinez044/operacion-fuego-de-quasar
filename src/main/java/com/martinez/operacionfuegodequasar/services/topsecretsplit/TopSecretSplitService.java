package com.martinez.operacionfuegodequasar.services.topsecretsplit;

import com.martinez.operacionfuegodequasar.dtos.request.InformationDTO;
import com.martinez.operacionfuegodequasar.dtos.response.SatelliteResponseDTO;

public interface TopSecretSplitService {

    void captureSatelliteInformation(String satelliteName, InformationDTO informationDTO);

    SatelliteResponseDTO getInformation();

}
