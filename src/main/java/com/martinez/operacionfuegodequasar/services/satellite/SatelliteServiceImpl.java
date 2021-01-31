package com.martinez.operacionfuegodequasar.services.satellite;

import com.martinez.operacionfuegodequasar.dtos.response.PositionDTO;
import com.martinez.operacionfuegodequasar.exceptions.NotFoundMessageException;
import com.martinez.operacionfuegodequasar.exceptions.NotFoundPositionException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class SatelliteServiceImpl implements SatelliteService {

    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SatelliteServiceImpl.class);

    /**
     * Obtiene la posición de la nave respecto a los 3 satelites kenobi, skywalker y sato.
     * Para obtener las coordenas x e y, Se aplica la fórmula deducida y explicada en el documento "Desarrollo-de-formula"
     *
     * @param distances
     * @return PositionDTO
     */
    @Override
    public PositionDTO getLocation(float... distances) {
        LOG.info("SatelliteServiceImpl.getLocation - {}", distances);

        validateInput(distances.length);

        float kenobiDistance = distances[0];
        float skywalkerDistance = distances[1];
        float satoDistance = distances[2];

        validationDistances(kenobiDistance, skywalkerDistance, satoDistance);
        float x = calculateX(kenobiDistance, skywalkerDistance, satoDistance);
        float y = calculateY(kenobiDistance, skywalkerDistance, satoDistance);

        return new PositionDTO(x, y);
    }

    /**
     * Genera un mensaje determinado por un conjunto de arrays pasados por parametro.
     * En caso de que falte una palabra el mensaje estará incompleto, no se podrá completar
     * y lanzara una NotFoundMessageException.
     *
     * @param messages
     * @return mensaje determinado.
     */
    @Override
    public String getMessage(String[]... messages) {
        LOG.info("SatelliteServiceImpl.getMessage - {}", messages);
        List<String> words = readMessages(messages);

        if (!isRead(words)) {
            LOG.error("The message could not be determined");
            throw new NotFoundMessageException("No se pudo determinar el mensaje");
        }

        words.remove("X");

        return joinWords(words);
    }

    /**
     * Explicación :
     * - Se utiliza HashMap para guardar clave, valor y no guardar dos veces el mismo valor(palabra).
     * - Se recorrer los mensajes en forma de matriz y se marcan las posiciones del hashmap  con una X. La X ayuda como referencia para saber si se pudo determinar el mensaje.
     * - El mensaje no se puede determinar cuando falta una palabra que no existe en ningun array de string.
     * - Si la palabra esta vacia directamente la ignora.
     * - Se guarda la palabra cuando la valor no existe en el hashmap.
     *
     * @param messages
     * @return Lista de String que son las palabras que componen el mensaje.
     */
    private List<String> readMessages(String[][] messages) {

        Map<Integer, String> messageMap = new HashMap();

        for (int i = 0; i < messages.length; i++) {
            for (int j = 0; j < messages[i].length; j++) {

                if (messageMap.get(j) == null) {
                    messageMap.put(j, "X");
                }

                if ("".equals(messages[i][j])) {
                    continue;
                }

                if (!messageMap.containsValue(messages[i][j])) {
                    messageMap.put(j, messages[i][j]);
                }
            }
        }

        return new ArrayList<>(messageMap.values());
    }

    /**
     * Recorre la lista de palabras y verifica que no existan cadenas "X".
     * Cuando se detecta una X y no es en la primera posición, significa que hubo una palabra del mensaje
     * que esta vacía en todos los array de cadenas de los mensajes recibidos, esto provoca que no se puede determinar el mensaje.
     * <p>
     * Si existe una X en la primera posicion, significa que hubo un desfasaje y la ignora.
     *
     * @param words
     * @return true si el mensaje fue leido.
     */
    private boolean isRead(List<String> words) {
        return (IntStream.range(0, words.size())
                .filter(i -> i != 0 && words.get(i).equals("X"))
                .count()) == 0;
    }

    private String joinWords(List<String> words) {
        String message = "";

        for (String word : words) {
            message += word + " ";
        }

        return message.trim();
    }

    /**
     * Se aplica la fórmula para encontrar x desarrollada en el doocumento "desarrollo-formula.pdf"
     *
     * @param kenobiDistance
     * @param skywalkerDistance
     * @param satoDistance
     * @return valor x de coordenada de la nave.
     */
    private Float calculateX(float kenobiDistance, float skywalkerDistance, float satoDistance) {
        return (float) (((Math.pow(kenobiDistance, 2) / 200f) - ((3 / 400f) * Math.pow(skywalkerDistance, 2)) + (Math.pow(satoDistance, 2) / 400f) - 1950) / 4f);
    }

    /**
     * Se aplica la fórmula para encontrar y desarrollada en el doocumento "desarrollo-formula.pdf"
     *
     * @param kenobiDistance
     * @param skywalkerDistance
     * @param satoDistance
     * @return valor y de coordenada de la nave.
     */
    private float calculateY(float kenobiDistance, float skywalkerDistance, float satoDistance) {
        return (float) (((Math.pow(kenobiDistance, 2) / 1200f) - ((5 / 2400f) * Math.pow(skywalkerDistance, 2)) + (Math.pow(satoDistance, 2) / 800f) - 525) / ((1 / 6f) - 0.5));
    }

    /**
     * Valida que no haya mas de una distancia en 0.0, Ya que se puede determinar la posición con una sola distancia en 0.0.
     *
     * @param kenobiDistance
     * @param skywalkerDistance
     * @param satoDistance
     */
    private void validationDistances(float kenobiDistance, float skywalkerDistance, float satoDistance) {

        if (kenobiDistance != 0.0 && skywalkerDistance != 0.0) {
            return;
        } else if (kenobiDistance != 0.0 && satoDistance != 0.0) {
            return;
        } else if (skywalkerDistance != 0.0 && satoDistance != 0.0) {
            return;
        } else {
            LOG.error("No se puedo determinar la posición - kenobi distance: [{}] - skywalker distance: [{}] - sato distance: [{}]", kenobiDistance, skywalkerDistance, satoDistance);
            throw new NotFoundPositionException("No se puedo determinar la posición");
        }
    }

    private void validateInput(int length) {
        if (length != 3) {
            throw new NotFoundPositionException("El calculo esta permitido para 3 satelites");
        }
    }

}

