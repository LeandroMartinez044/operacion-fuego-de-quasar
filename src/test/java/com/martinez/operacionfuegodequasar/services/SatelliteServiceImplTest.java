package com.martinez.operacionfuegodequasar.services;

import com.martinez.operacionfuegodequasar.dtos.response.PositionDTO;
import com.martinez.operacionfuegodequasar.exceptions.NotFoundMessageException;
import com.martinez.operacionfuegodequasar.exceptions.NotFoundPositionException;
import com.martinez.operacionfuegodequasar.services.satellite.SatelliteServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SatelliteServiceImplTest {

    @InjectMocks
    private SatelliteServiceImpl satelliteService;


    @Test
    public void Position_Ok() {
        PositionDTO positionDTO1 = satelliteService.getLocation(447.0f, 500.0f, 806.0f);
        assertEquals(-300f, positionDTO1.getX(), 10);
        assertEquals(200f, positionDTO1.getY(), 10);

        PositionDTO positionDTO2 = satelliteService.getLocation(1000f, 412f, 300f);
        assertEquals(500f, positionDTO2.getX(), 10);
        assertEquals(-200f, positionDTO2.getY(), 10);

        PositionDTO positionDTO3 = satelliteService.getLocation(1342f, 781f, 361f);
        assertEquals(700f, positionDTO3.getX(), 10);
        assertEquals(400f, positionDTO3.getY(), 10);

    }

    @Test
    public void More_The_Two_Distances_In_Zero() {
        Assertions.assertThrows(NotFoundPositionException.class, () -> {
            satelliteService.getLocation(0.0f, 0.0f, 806.0f);
        });

        Assertions.assertThrows(NotFoundPositionException.class, () -> {
            satelliteService.getLocation(0.0f, 500.0f, 0.0f);
        });

        Assertions.assertThrows(NotFoundPositionException.class, () -> {
            satelliteService.getLocation(447.0f, 0.0f, 0.0f);
        });

    }

    @Test
    public void More_Or_Less_Of_Three_Distances() {
        Assertions.assertThrows(NotFoundPositionException.class, () -> {
            satelliteService.getLocation(0.0f, 0.0f, 806.0f, 10f);
        });

        Assertions.assertThrows(NotFoundPositionException.class, () -> {
            satelliteService.getLocation(806.0f, 10f);
        });
    }

    @Test
    public void Message_OK() {
        String[] kenobiMessage = {"este", "", "", "mensaje", ""};
        String[] skywalkerMessage = {"", "es", "", "", "secreto"};
        String[] satoMessage = {"este", "", "un", "", ""};

        assertEquals("este es un mensaje secreto", satelliteService.getMessage(kenobiMessage, skywalkerMessage, satoMessage));
    }

    @Test
    public void Message_Desfasado_OK() {
        String[] kenobiMessage = {"", "este", "es", "un", "mensaje"};
        String[] skywalkerMessage = {"este", "", "un", "mensaje"};
        String[] satoMessage = {"", "", "es", "", "mensaje"};

        assertEquals("este es un mensaje", satelliteService.getMessage(kenobiMessage, skywalkerMessage, satoMessage));
    }

    @Test
    public void Message_Error() {
        String[] kenobiMessage = {"", "", "", "un", "mensaje"};
        String[] skywalkerMessage = {"este", "", "un", "mensaje"};
        String[] satoMessage = {"", "", "", "", "mensaje"};

        Assertions.assertThrows(NotFoundMessageException.class, () -> {
            satelliteService.getMessage(kenobiMessage, skywalkerMessage, satoMessage);
        });
    }
}