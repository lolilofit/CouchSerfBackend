package main.service;

import main.tables.Place;

import java.util.List;

public interface PlaceService {
    public void addNewPlace(String country, String city, String home);
    public List<Place> getPlaceWithFilters(String country, String city, String home);
}
