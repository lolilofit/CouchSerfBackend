package nsu.fit.upprpo.csbackend.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "comm")
public class Comment implements Serializable {
    private static final Logger logger = Logger.getLogger(Comment.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "comment_generator")
    @SequenceGenerator(name = "comment_generator", sequenceName = "COMMENT_SEQ")
    @Column(name = "comment_id")
    private Long commentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Advert commentAdvert;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User author;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @Override
    public int hashCode() {
        return message.hashCode() + author.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Comment))
            return false;
        Comment comment = (Comment) o;
        return message.equals(comment.getMessage()) && author.equals(comment.getAuthor());
    }
}
