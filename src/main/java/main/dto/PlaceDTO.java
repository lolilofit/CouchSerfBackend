package main.dto;

import java.io.Serializable;

public class PlaceDTO implements Serializable {
    private Long placeId;
    private String country;
    private String city;
    private String home;

    public PlaceDTO() {}

    public PlaceDTO(String country, String city, String home) {
        this.city = city;
        this.country = country;
        this.home = home;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getHome() {
        return home;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }
}
