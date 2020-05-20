package nsu.fit.upprpo.csbackend.repository;

import nsu.fit.upprpo.csbackend.tables.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {
    List<Place> findByPlaceId(Long placeId);
    List<Place> findByCountryAndCityAndHome(String country, String city, String home);
}
