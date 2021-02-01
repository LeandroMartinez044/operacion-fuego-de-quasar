package com.martinez.operacionfuegodequasar.services.topsecret;

import com.martinez.operacionfuegodequasar.dtos.response.PositionDTO;
import com.martinez.operacionfuegodequasar.dtos.response.SatelliteResponseDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteRequestDTO;
import com.martinez.operacionfuegodequasar.exceptions.InformationRequiredException;
import com.martinez.operacionfuegodequasar.services.satellite.SatelliteService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TopSecretServiceImpl implements TopSecretService {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TopSecretServiceImpl.class);

    private SatelliteService satelliteService;

    @Autowired
    public TopSecretServiceImpl(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    /**
     * Recibe una SatelliteRequestDTO que contiene una lista de la información de los satélites.
     * El metodo extrae la distancias  y los array de cadenas para que sean procesados por el servicio "SatelliteService"
     *
     * @param satelliteRequestDTO
     * @return Localización del eje x e y de la nave y el mensaje generado.
     */
    @Override
    public SatelliteResponseDTO processInformation(SatelliteRequestDTO satelliteRequestDTO) {
        LOG.info("TopSecretServiceImpl.processInformationFromSatellites");

        Map<String, Float> distances = extractDistances(satelliteRequestDTO.getSatellites());
        PositionDTO positionDTO = satelliteService.getLocation(distances.get("kenobi"), distances.get("skywalker"), distances.get("sato"));

        String[][] messagesToRead = extractMessagesToRead(satelliteRequestDTO.getSatellites());
        String message = satelliteService.getMessage(messagesToRead);

        return new SatelliteResponseDTO(positionDTO, message);
    }

    /**
     * Genera un Map con el nombre del satélite y la distancia a la nave.
     * Si no tiene la información de todos los satélites lanza la exception InformationRequeredException.
     *
     * @param satellites
     * @return Map con nombre del satelite y distancia.
     */
    private Map<String, Float> extractDistances(List<SatelliteDTO> satellites) {
        Map<String, Float> satellitesMap = satellites.stream()
                .collect((Collectors.toMap(SatelliteDTO::getName, SatelliteDTO::getDistance)));

        if (!satellitesMap.containsKey("kenobi") || !satellitesMap.containsKey("skywalker") || !satellitesMap.containsKey("sato")) {
            LOG.error("Not found distances from all satellites");
            throw new InformationRequiredException("No hay suficiente información");
        }

        return satellitesMap;
    }

    /**
     * Extrae los arrays de palabras de los satélites.
     * Debe al menos existir un array, en caso de que no exista
     * lanzara una exception InformationRequiredException.
     *
     * @param satellites
     * @return arrays de palabras
     */
    private String[][] extractMessagesToRead(List<SatelliteDTO> satellites) {

        int messagesSize = (int) calculateSizeMessages(satellites);

        if (messagesSize == 0) {
            LOG.error("requires at least one array");
            throw new InformationRequiredException("No hay suficiente información");
        }

        String[][] messages = new String[messagesSize][];

        for (int i = 0; i < satellites.size(); i++) {
            if (satellites.get(i).getMessage() != null) {
                messages[i] = satellites.get(i).getMessage();
            }
        }

        return messages;
    }

    private long calculateSizeMessages(List<SatelliteDTO> satellites) {
        return satellites.stream()
                .filter(satelliteDTO -> satelliteDTO.getMessage() != null)
                .count();
    }

}
