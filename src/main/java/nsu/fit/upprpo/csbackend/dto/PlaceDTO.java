package nsu.fit.upprpo.csbackend.dto;

import lombok.Data;
import nsu.fit.upprpo.csbackend.PublicUtils;

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

    @Override
    public String toString() {
        return PublicUtils.publicToString(this);
    }

    @Override
    public int hashCode() {
        return country.hashCode() + city.hashCode() + home.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof PlaceDTO))
            return false;
        PlaceDTO place = (PlaceDTO) o;
        return country.equals(place.getCountry()) && city.equals(place.getCity()) && home.equals(place.getHome());
    }
}
