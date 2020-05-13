package main.tables;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "comment_generator")
    @SequenceGenerator(name = "comment_generator", sequenceName = "COMMENT_SEQ")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "adId")
    private Advert commentAdvert;

    @Column(name = "message", nullable = false)
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
