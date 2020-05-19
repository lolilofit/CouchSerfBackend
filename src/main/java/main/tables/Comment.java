package main.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Advert getCommentAdvert() {
        return commentAdvert;
    }

    public Long getCommentId() {
        return commentId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCommentAdvert(Advert commentAdvert) {
        this.commentAdvert = commentAdvert;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
