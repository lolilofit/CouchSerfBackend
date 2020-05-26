package nsu.fit.upprpo.csbackend.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nsu.fit.upprpo.csbackend.PublicUtils;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "comm")
public class Comment implements Serializable {
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
        return PublicUtils.publicToString(this);
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
