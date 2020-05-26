package nsu.fit.upprpo.csbackend.tables;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import nsu.fit.upprpo.csbackend.dto.PlaceDTO;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Place",
        uniqueConstraints=@UniqueConstraint(columnNames={"country", "city", "home"}))
public class Place implements Serializable {
    private static final Logger logger = Logger.getLogger(Place.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "place_generator")
    @SequenceGenerator(name = "place_generator", sequenceName = "PLACE_SEQ")
    private Long placeId;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "home")
    private String home;

    public Place() {}

    public Place(PlaceDTO placeDTO) {
        this.home = placeDTO.getHome();
        this.country = placeDTO.getCountry();
        this.city = placeDTO.getCity();
        this.placeId = placeDTO.getPlaceId();
    }

    public Place(String country, String city, String home) {
        this.city = city;
        this.country = country;
        this.home = home;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @Override
    public int hashCode() {
        return country.hashCode() + city.hashCode() + home.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Place))
            return false;
        Place place = (Place) o;
        return country.equals(place.getCountry()) && city.equals(place.getCity()) && home.equals(place.getHome());
    }
}
