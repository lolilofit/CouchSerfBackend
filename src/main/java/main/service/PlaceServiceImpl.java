package main.service;

import main.repository.PlaceRepository;
import main.tables.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService{
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public void addNewPlace(String country, String city, String home) {
        placeRepository.save(new Place(country, city, home));
    }

    @Override
    public List<Place> getPlaceWithFilters(String country, String city, String home) {
        if(country != null && city != null && home != null) {
            return placeRepository.findByCountryAndCityAndHome(country, city, home);
        }

        //rewrite
        return null;
    }
}
