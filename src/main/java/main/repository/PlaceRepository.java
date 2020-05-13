package main.repository;

import main.tables.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {
    public List<Place> findByPlaceId(Long placeId);
    //public List<Place> findByCityAndCountry();
    //public List<Place> findByCountry();
    //public List<Place> findByCityAndAndCountry();
    public List<Place> findByCountryAndCityAndHome(String country, String city, String home);
}
