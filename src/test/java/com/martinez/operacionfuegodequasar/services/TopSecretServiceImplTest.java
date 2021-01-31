package com.martinez.operacionfuegodequasar.services;

import com.martinez.operacionfuegodequasar.dtos.response.PositionDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteRequestDTO;
import com.martinez.operacionfuegodequasar.exceptions.InformationRequiredException;
import com.martinez.operacionfuegodequasar.services.satellite.SatelliteService;
import com.martinez.operacionfuegodequasar.services.topsecret.TopSecretServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopSecretServiceImplTest {

    @InjectMocks
    private TopSecretServiceImpl topSecretService;

    @Mock
    private SatelliteService satelliteService;

    @Captor
    ArgumentCaptor<float[]> distancesArgumentCaptor;

    @Captor
    ArgumentCaptor<String[][]> messageToReadArgumentCaptor;

    @Before
    public void setup() {
        topSecretService = new TopSecretServiceImpl(satelliteService);
    }

    @Test
    public void processInformationOfTheSpaceCraft_OK() {

        when(satelliteService.getLocation(any())).thenReturn(new PositionDTO(-300, 200));
        when(satelliteService.getMessage(any())).thenReturn("este es un mensaje secreto");

        topSecretService.processInformationFromSatellites(getRequest_OK());

        verify(satelliteService).getLocation(distancesArgumentCaptor.capture());
        verify(satelliteService).getMessage(messageToReadArgumentCaptor.capture());

        assertEquals(3, distancesArgumentCaptor.getAllValues().size());
        assertEquals(3, messageToReadArgumentCaptor.getAllValues().size());

    }

    @Test
    public void Less_Of_Three_Satellites() {
        Assertions.assertThrows(InformationRequiredException.class, () -> {
            topSecretService.processInformationFromSatellites(getRequestLessOfThreeSatellites());
        });
        verify(satelliteService, times(0)).getLocation(any());
        verify(satelliteService, times(0)).getMessage(any());
    }

    @Test
    public void Without_Messages() {
        Assertions.assertThrows(InformationRequiredException.class, () -> {
            topSecretService.processInformationFromSatellites(getRequestWithoutMessage());
        });

        verify(satelliteService).getLocation(any());
        verify(satelliteService, times(0)).getMessage(any());
    }

    private SatelliteRequestDTO getRequestWithoutMessage() {
        SatelliteRequestDTO satelliteRequestDTO = new SatelliteRequestDTO();
        List<SatelliteDTO> satelliteDTOList = new ArrayList<>();

        SatelliteDTO satelliteDTO1 = new SatelliteDTO();
        satelliteDTO1.setName("kenobi");
        satelliteDTO1.setDistance(447.0f);

        SatelliteDTO satelliteDTO2 = new SatelliteDTO();
        satelliteDTO2.setName("skywalker");
        satelliteDTO2.setDistance(500.0f);

        SatelliteDTO satelliteDTO3 = new SatelliteDTO();
        satelliteDTO3.setName("sato");
        satelliteDTO3.setDistance(806.0f);

        satelliteDTOList.add(satelliteDTO1);
        satelliteDTOList.add(satelliteDTO2);
        satelliteDTOList.add(satelliteDTO3);

        satelliteRequestDTO.setSatellites(satelliteDTOList);

        return satelliteRequestDTO;
    }

    private SatelliteRequestDTO getRequestLessOfThreeSatellites() {
        SatelliteRequestDTO satelliteRequestDTO = new SatelliteRequestDTO();
        List<SatelliteDTO> satelliteDTOList = new ArrayList<>();

        SatelliteDTO satelliteDTO2 = new SatelliteDTO();
        satelliteDTO2.setName("skywalker");
        satelliteDTO2.setDistance(500.0f);
        satelliteDTO2.setMessage(new String[]{"", "es", "", "", "secreto"});

        SatelliteDTO satelliteDTO3 = new SatelliteDTO();
        satelliteDTO3.setName("sato");
        satelliteDTO3.setDistance(806.0f);
        satelliteDTO3.setMessage(new String[]{"este", "", "un", "", ""});

        satelliteDTOList.add(satelliteDTO2);
        satelliteDTOList.add(satelliteDTO3);

        satelliteRequestDTO.setSatellites(satelliteDTOList);

        return satelliteRequestDTO;
    }

    private SatelliteRequestDTO getRequest_OK() {
        SatelliteRequestDTO satelliteRequestDTO = new SatelliteRequestDTO();
        List<SatelliteDTO> satelliteDTOList = new ArrayList<>();

        SatelliteDTO satelliteDTO1 = new SatelliteDTO();
        satelliteDTO1.setName("kenobi");
        satelliteDTO1.setDistance(447.0f);
        satelliteDTO1.setMessage(new String[]{"este", "", "", "mensaje", ""});

        SatelliteDTO satelliteDTO2 = new SatelliteDTO();
        satelliteDTO2.setName("skywalker");
        satelliteDTO2.setDistance(500.0f);
        satelliteDTO2.setMessage(new String[]{"", "es", "", "", "secreto"});

        SatelliteDTO satelliteDTO3 = new SatelliteDTO();
        satelliteDTO3.setName("sato");
        satelliteDTO3.setDistance(806.0f);
        satelliteDTO3.setMessage(new String[]{"este", "", "un", "", ""});

        satelliteDTOList.add(satelliteDTO1);
        satelliteDTOList.add(satelliteDTO2);
        satelliteDTOList.add(satelliteDTO3);

        satelliteRequestDTO.setSatellites(satelliteDTOList);

        return satelliteRequestDTO;
    }

}