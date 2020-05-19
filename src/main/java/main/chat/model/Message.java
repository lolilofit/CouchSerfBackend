package main.chat.model;

public class Message {
    private String fromUsername;
    private String toUsername;
    private String content;

    public String getContent() {
        return content;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

}
