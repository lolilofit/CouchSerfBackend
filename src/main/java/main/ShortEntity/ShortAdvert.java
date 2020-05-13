package main.ShortEntity;


import java.util.Date;

public class ShortAdvert {
    private String username;
    private String header;
    private String message;
    private String country;
    private String city;
    private String home;
    private int peopleNumber;
    private Date arrivingDate;
    private Date checkOutDate;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public void setArrivingDate(Date arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Date getArrivingDate() {
        return arrivingDate;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public String getHeader() {
        return header;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHome() {
        return home;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
