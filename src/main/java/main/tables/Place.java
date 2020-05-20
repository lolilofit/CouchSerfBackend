package main.tables;

import lombok.Data;
import main.dto.PlaceDTO;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Place",
        uniqueConstraints=@UniqueConstraint(columnNames={"country", "city", "home"}))
public class Place implements Serializable {
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
}
