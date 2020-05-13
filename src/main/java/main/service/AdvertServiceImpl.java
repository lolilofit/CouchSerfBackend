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

    @Override
    public void addNewAdvert(Long owner_id,
                             String header,
                             String message,
                             Integer peopleNumber,
                             Date arrivingDate,
                             Date checkOutDate,
                             //Place place,
                             String country,
                             String city,
                             String home,
                             AdvertType advertType) {
        List<User> owners = usersRepository.findUsersByUserid(owner_id);
        if(owners.size() == 0)
            return;
        if(checkOutDate.compareTo(arrivingDate) < 0)
            return;

        placeService.addNewPlace(country, city, home);
        List<Place> places = placeService.getPlaceWithFilters(country, city, home);
        if(places.size() == 0)
            return;
        Place place = places.get(0);

        User owner = owners.get(0);
        Advert advert = new Advert();

        advert.setOwner(owner);
        advert.setPublicationDate(new Date(System.currentTimeMillis()));
        advert.setHeader(header);
        advert.setMessage(message);
        advert.setAdvertType(advertType);
        advert.setPeopleNumber(peopleNumber);
        advert.setArrivingDate(arrivingDate);
        advert.setCheckOutDate(checkOutDate);
        advert.setPlace(place);

        advertRepository.save(advert);
    }
}
