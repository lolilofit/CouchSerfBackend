package main.dto;

import main.tables.AdvertType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdvertDTO implements Serializable {
    private Long adId;
    private UserDTO owner;
    private Date publicationDate;
    private List<UserDTO> subscribers;
    private String header;
    private String message;
    private AdvertType advertType;
    private PlaceDTO place;
    private int peopleNumber;
    private Date arrivingDate;
    private Date checkOutDate;

    public AdvertDTO() {
        subscribers = new ArrayList<>();
    }

    public List<UserDTO> getSubscribers() {
        return subscribers;
    }

    public Long getAdId() {
        return adId;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public void setSubscribers(List<UserDTO> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(UserDTO user) {
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

    public PlaceDTO getPlace() {
        return place;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public void setArrivingDate(Date arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public void setPlace(PlaceDTO place) {
        this.place = place;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }


}
