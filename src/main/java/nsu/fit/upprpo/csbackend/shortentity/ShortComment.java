package nsu.fit.upprpo.csbackend.shortentity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class ShortComment {
    private String message;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof  ShortComment))
            return false;
        ShortComment shortComment = (ShortComment) o;
        return message.equals(shortComment.getMessage());
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
