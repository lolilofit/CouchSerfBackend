package nsu.fit.upprpo.csbackend.shortentity;

import lombok.Data;
import nsu.fit.upprpo.csbackend.PublicUtils;

@Data
public class ShortComment {
    private String message;

    @Override
    public String toString() {
        return PublicUtils.publicToString(this);
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
