package nsu.fit.upprpo.csbackend.service;

import nsu.fit.upprpo.csbackend.tables.Place;

import java.util.List;

public interface PlaceService {
    void addNewPlace(String country, String city, String home);
    List<Place> getPlaceWithFilters(String country, String city, String home);
}
