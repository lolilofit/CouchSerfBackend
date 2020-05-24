package nsu.fit.upprpo.csbackend.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "comm")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "comment_generator")
    @SequenceGenerator(name = "comment_generator", sequenceName = "COMMENT_SEQ")
    @Column(name = "commentId")
    private Long commentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "adId")
    private Advert commentAdvert;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User author;

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
