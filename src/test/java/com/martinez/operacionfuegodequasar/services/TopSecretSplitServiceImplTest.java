package com.martinez.operacionfuegodequasar.services;

import com.martinez.operacionfuegodequasar.dtos.request.InformationDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteDTO;
import com.martinez.operacionfuegodequasar.dtos.request.SatelliteRequestDTO;
import com.martinez.operacionfuegodequasar.dtos.response.PositionDTO;
import com.martinez.operacionfuegodequasar.dtos.response.SatelliteResponseDTO;
import com.martinez.operacionfuegodequasar.exceptions.IncorrectSatelliteNameException;
import com.martinez.operacionfuegodequasar.exceptions.InformationRequiredException;
import com.martinez.operacionfuegodequasar.services.topsecret.TopSecretServiceImpl;
import com.martinez.operacionfuegodequasar.services.topsecretsplit.TopSecretSplitServiceImpl;
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
public class TopSecretSplitServiceImplTest {

    @InjectMocks
    private TopSecretSplitServiceImpl topSecretSplitService;

    @Mock
    private TopSecretServiceImpl topSecretService;

    @Captor
    private ArgumentCaptor<SatelliteRequestDTO> satelliteRequestDTOArgumentCaptor;

    @Before
    public void setup() {
        topSecretSplitService = new TopSecretSplitServiceImpl(topSecretService);
    }

    @Test
    public void captureSatelliteInformation_Ok(){

        when(topSecretService.processInformation(any())).thenReturn(any());

        InformationDTO informationDTO = new InformationDTO();

        informationDTO.setDistance(447.0f);
        informationDTO.setMessage(new String[]{"este", "", "", "mensaje", ""});
        topSecretSplitService.captureSatelliteInformation("kenobi", informationDTO);

        informationDTO.setDistance(500.0f);
        informationDTO.setMessage(new String[]{"", "es", "", "", "secreto"});
        topSecretSplitService.captureSatelliteInformation("skywalker", informationDTO);

        informationDTO.setDistance(806.0f);
        informationDTO.setMessage(new String[]{"este", "", "un", "", ""});
        topSecretSplitService.captureSatelliteInformation("sato", informationDTO);

        topSecretSplitService.getInformation();

        verify(topSecretService).processInformation(satelliteRequestDTOArgumentCaptor.capture());

        SatelliteRequestDTO satelliteRequestDTO = satelliteRequestDTOArgumentCaptor.getValue();

        for(int i = 0; i < 3; i++){
            assertEquals(data_list_OK().getSatellites().get(i).getName(), satelliteRequestDTO.getSatellites().get(i).getName());
            assertEquals(data_list_OK().getSatellites().get(i).getDistance(), satelliteRequestDTO.getSatellites().get(i).getDistance(), 10);
            assertArrayEquals(data_list_OK().getSatellites().get(i).getMessage(), satelliteRequestDTO.getSatellites().get(i).getMessage());
        }

    }

    @Test
    public void captureSatelliteInformation_With_Update_Ok(){

        when(topSecretService.processInformation(any())).thenReturn(any());

        InformationDTO informationDTO = new InformationDTO();

        informationDTO.setDistance(447.0f);
        informationDTO.setMessage(new String[]{"este", "", "", "mensaje", ""});
        topSecretSplitService.captureSatelliteInformation("kenobi", informationDTO);

        informationDTO.setDistance(500.0f);
        informationDTO.setMessage(new String[]{"", "es", "", "", "secreto"});
        topSecretSplitService.captureSatelliteInformation("skywalker", informationDTO);

        informationDTO.setDistance(700.45f);
        informationDTO.setMessage(new String[]{"este", "es", "", "", "secreto"});
        topSecretSplitService.captureSatelliteInformation("skywalker", informationDTO);

        informationDTO.setDistance(806.0f);
        informationDTO.setMessage(new String[]{"este", "", "un", "", ""});
        topSecretSplitService.captureSatelliteInformation("sato", informationDTO);

        topSecretSplitService.getInformation();

        verify(topSecretService).processInformation(satelliteRequestDTOArgumentCaptor.capture());

        SatelliteRequestDTO satelliteRequestDTO = satelliteRequestDTOArgumentCaptor.getValue();


        assertEquals("skywalker", satelliteRequestDTO.getSatellites().get(1).getName());
        assertEquals(700.45f, satelliteRequestDTO.getSatellites().get(1).getDistance(), 10);
        assertArrayEquals(new String[]{"este", "es", "", "", "secreto"}, satelliteRequestDTO.getSatellites().get(1).getMessage());


    }

    @Test
    public void Incorrect_Satellite_name(){
        InformationDTO informationDTO = new InformationDTO();
        informationDTO.setDistance(447.0f);
        informationDTO.setMessage(new String[]{"este", "", "", "mensaje", ""});

        Assertions.assertThrows(IncorrectSatelliteNameException.class, () -> {
            topSecretSplitService.captureSatelliteInformation("keno", informationDTO);
        });

    }

    @Test
    public void getInformation_Ok(){
        when(topSecretService.processInformation(any())).thenReturn(new SatelliteResponseDTO(new PositionDTO(-300, 200), "este es un mensaje secreto"));

        SatelliteResponseDTO satelliteResponseDTO = topSecretSplitService.getInformation();
        assertEquals("este es un mensaje secreto", satelliteResponseDTO.getMessage());
        assertEquals(-300.0, satelliteResponseDTO.getPosition().getX(),10);
        assertEquals(200.0, satelliteResponseDTO.getPosition().getY(),10);

    }

    private SatelliteRequestDTO data_list_OK() {
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