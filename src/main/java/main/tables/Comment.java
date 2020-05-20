package main.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

}
