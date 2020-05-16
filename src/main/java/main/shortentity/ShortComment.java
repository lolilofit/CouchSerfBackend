package main.shortentity;

public class ShortComment {
    private Long adId;
    private String message;
    //what to use instead of user
    private String author;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getAdId() {
        return adId;
    }

    public String getAuthor() {
        return author;
    }
}
