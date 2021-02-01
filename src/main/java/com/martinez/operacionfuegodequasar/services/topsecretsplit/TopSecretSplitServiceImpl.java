package com.martinez.operacionfuegodequasar.services.topsecretsplit;

import com.martinez.operacionfuegodequasar.dtos.request.SatelliteDTO;
import com.martinez.operacionfuegodequasar.dtos.request.InformationDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteRequestDTO;
import com.martinez.operacionfuegodequasar.dtos.response.SatelliteResponseDTO;
import com.martinez.operacionfuegodequasar.exceptions.IncorrectSatelliteNameException;
import com.martinez.operacionfuegodequasar.services.topsecret.TopSecretService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopSecretSplitServiceImpl implements TopSecretSplitService {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TopSecretSplitServiceImpl.class);

    private List<SatelliteDTO> satellites = new ArrayList<>();

    private final TopSecretService topSecretService;

    @Autowired
    public TopSecretSplitServiceImpl(TopSecretService topSecretService) {
        this.topSecretService = topSecretService;
    }

    /**
     * Guarda la informacion del satelite en un Arraylist. Si no fue creado actualiza la informacion.
     * Si el nombre del satélite no existe entonces lanza una exception IncorrectSatelliteNameException
     *
     * @param satelliteName
     * @param informationDTO
     */
    @Override
    public void captureSatelliteInformation(String satelliteName, InformationDTO informationDTO) {
        LOG.info("TopSecretSplitServiceImpl.captureSatelliteInformation -satelliteName: [{}] -distances: [{}] -messages: [{}]", satelliteName, informationDTO.getDistance(), informationDTO.getMessage());

        validateName(satelliteName);
        Optional<SatelliteDTO> currentSatellite = findSatelliteByName(satelliteName);

        if (currentSatellite.isPresent()) {
            updateInformationOfSatellite(currentSatellite.get(), informationDTO);
        } else {
            createInformationOfSatellite(new SatelliteDTO(satelliteName, informationDTO.getDistance(), informationDTO.getMessage()));
        }
    }

    /**
     * Consume el servicio processInformation de topSecretService.
     * Utiliza el ArrayList con los datos que fueron al almacenados.
     * Una vez la operación fue exitosa, resea los valores
     *
     * @return posición x e y, y el mensaje obtenido.
     */
    @Override
    public SatelliteResponseDTO getInformation() {
        LOG.info("TopSecretSplitServiceImpl.captureSatelliteInformation");

        SatelliteRequestDTO satelliteRequestDTO = new SatelliteRequestDTO();
        satelliteRequestDTO.setSatellites(satellites);
        SatelliteResponseDTO satelliteResponseDTO = topSecretService.processInformation(satelliteRequestDTO);
        satellites = new ArrayList<>();

        return satelliteResponseDTO;
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
        if (!satelliteName.equals("kenobi") && !satelliteName.equals("skywalker") && !satelliteName.equals("sato")) {
            throw new IncorrectSatelliteNameException("No existe satélite con el nombre: " + satelliteName);
        }
    }
}