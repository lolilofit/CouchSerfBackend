package main.repository;

import main.tables.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {
    public List<Place> findByPlaceId(Long placeId);
    public List<Place> findByCountryAndCityAndHome(String country, String city, String home);
    public List<Place> findByCountryAndCity(String country, String city);
    public List<Place> findByCountryAndHome(String country, String home);
    public List<Place> findByCityAndHome(String city, String home);
    public List<Place> findByCity(String city);
    public List<Place> findByCountry(String country);
    public List<Place> findByHome(String home);
}
