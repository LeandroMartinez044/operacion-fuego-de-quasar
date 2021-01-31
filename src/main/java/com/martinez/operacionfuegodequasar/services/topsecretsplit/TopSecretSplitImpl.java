package com.martinez.operacionfuegodequasar.services.topsecretsplit;

import com.martinez.operacionfuegodequasar.dtos.request.SatelliteDTO;
import com.martinez.operacionfuegodequasar.dtos.request.InformationDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteRequestDTO;
import com.martinez.operacionfuegodequasar.dtos.response.SatelliteResponseDTO;
import com.martinez.operacionfuegodequasar.exceptions.IncorrectSatelliteNameException;
import com.martinez.operacionfuegodequasar.services.topsecret.TopSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopSecretSplitImpl implements TopSecretSplitService {

    private final List<SatelliteDTO> satellites = new ArrayList<>();

    private final TopSecretService topSecretService;

    @Autowired
    public TopSecretSplitImpl(TopSecretService topSecretService){
        this.topSecretService = topSecretService;
    }


    @Override
    public void captureSatelliteInformation(String satelliteName, InformationDTO informationDTO) {
        validateName(satelliteName);
        Optional<SatelliteDTO> currentSatellite = findSatelliteByName(satelliteName);

        if(currentSatellite.isPresent()){
            updateInformationOfSatellite(currentSatellite.get(), informationDTO);
        }else{
            createInformationOfSatellite(new SatelliteDTO(satelliteName, informationDTO.getDistance(), informationDTO.getMessage()));
        }
    }

    @Override
    public SatelliteResponseDTO getInformation() {
        SatelliteRequestDTO satelliteRequestDTO = new SatelliteRequestDTO();
        satelliteRequestDTO.setSatellites(satellites);
        return topSecretService.processInformationFromSatellites(satelliteRequestDTO);
    }

    private Optional<SatelliteDTO> findSatelliteByName(String satelliteName) {
        return this.satellites.stream()
                .filter(satelliteDTO -> satelliteDTO.getName().equals(satelliteName))
                .findFirst();
    }

    private void createInformationOfSatellite(SatelliteDTO satelliteDTO) {
        this.satellites.add(satelliteDTO);
    }

    private void updateInformationOfSatellite(SatelliteDTO satelliteDTO, InformationDTO informationDTO) {
        satelliteDTO.setMessage(informationDTO.getMessage());
        satelliteDTO.setDistance(informationDTO.getDistance());
    }

    private void validateName(String satelliteName) {
        if(!satelliteName.equals("kenobi") && !satelliteName.equals("skywalker") && !satelliteName.equals("sato")){
            throw new IncorrectSatelliteNameException("No existe satelite con el nombre: " + satelliteName);
        }
    }
}