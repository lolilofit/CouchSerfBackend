package main.service;

import main.tables.Place;

import java.util.List;

public interface PlaceService {
    void addNewPlace(String country, String city, String home);
    List<Place> getPlaceWithFilters(String country, String city, String home);
}
