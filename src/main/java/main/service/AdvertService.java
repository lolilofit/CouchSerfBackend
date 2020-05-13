package main.service;

import main.tables.Advert;
import main.tables.AdvertType;
import main.tables.Place;

import java.util.Date;
import java.util.List;

public interface AdvertService {
    public List<Advert> getAllAdverts();

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
                             AdvertType advertType);
}
