package main.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Advert")
public class Advert implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ad_generator")
    @SequenceGenerator(name = "ad_generator", sequenceName = "AD_SEQ")
    @Column(name = "adId")
    private Long adId;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;

    @Column(name = "publicationDate")
    private Date publicationDate;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ADVERT_USER",
            joinColumns = {@JoinColumn(name = "ownerId")},
            inverseJoinColumns = {@JoinColumn(name = "userid")})
    private List<User> subscribers;

    //short header for advert on site
    @Column(name = "header")
    private String header;

    @Column(name = "message")
    private String message;

    //@Column(name = "advert_type")
    @Enumerated(EnumType.STRING)
    private AdvertType advertType;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "people_number")
    private int peopleNumber;

    @Column(name = "arriving_date")
    private Date arrivingDate;

    @Column(name = "check_out_date")
    private Date checkOutDate;


    public Advert() {
        subscribers = new ArrayList<>();
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public Long getAdId() {
        return adId;
    }

    public User getOwner() {
        return owner;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(User user) {
        subscribers.add(user);
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public AdvertType getAdvertType() {
        return advertType;
    }

    public void setAdvertType(AdvertType advertType) {
        this.advertType = advertType;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getArrivingDate() {
        return arrivingDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public Place getPlace() {
        return place;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public void setArrivingDate(Date arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
