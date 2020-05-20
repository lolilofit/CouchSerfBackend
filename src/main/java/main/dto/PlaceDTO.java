package main.dto;

import lombok.Data;

import java.io.Serializable;

@Data
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
}
