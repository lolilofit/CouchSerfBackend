package nsu.fit.upprpo.csbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

public class PublicUtils {
    private static Logger logger = Logger.getLogger(PublicUtils.class);

    public static String publicToString(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }
}
