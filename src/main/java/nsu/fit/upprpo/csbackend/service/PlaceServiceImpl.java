package nsu.fit.upprpo.csbackend.service;

import nsu.fit.upprpo.csbackend.repository.PlaceRepository;
import nsu.fit.upprpo.csbackend.tables.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(country != null && city != null && home != null)
            return placeRepository.findByCountryAndCityAndHome(country, city, home);

        return new ArrayList<>();
    }
}
