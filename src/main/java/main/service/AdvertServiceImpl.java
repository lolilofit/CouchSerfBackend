package main.service;

import main.repository.AdvertRepository;
import main.repository.UsersRepository;
import main.tables.Advert;
import main.tables.AdvertType;
import main.tables.Place;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PlaceService placeService;

    @Override
    public List<Advert> getAllAdverts() {
        List<Advert> result = new ArrayList<>();

        Iterable<Advert> adverts = advertRepository.findAll();
        adverts.forEach(result::add);

        return result;
    }

    private Place addPlaceIfNotPresent(String country, String city, String home) {
        List<Place> places = placeService.getPlaceWithFilters(country, city, home);
        if(places.isEmpty()) {
            placeService.addNewPlace(country, city, home);
            places = placeService.getPlaceWithFilters(country, city, home);
            if (places.isEmpty())
                return null;
        }
        return places.get(0);
    }

    @Override
    public Advert addNewAdvert(Advert advert, User owner) {
        if(advert.getCheckOutDate().compareTo(advert.getArrivingDate()) < 0)
            return null;

        Place place = advert.getPlace();
        place = addPlaceIfNotPresent(place.getCountry(), place.getCity(), place.getHome());
        if(place == null)
            return null;

        advert.setOwner(owner);
        advert.setPublicationDate(new Date(System.currentTimeMillis()));
        advert.setPlace(place);

        return advertRepository.save(advert);
    }
}
