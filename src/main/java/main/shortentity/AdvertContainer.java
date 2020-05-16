package main.shortentity;

import main.tables.Advert;
import main.tables.Comment;

import java.util.List;

public class AdvertContainer {
    private List<Comment> comments;
    private Advert advert;

    public Advert getAdvert() {
        return advert;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
