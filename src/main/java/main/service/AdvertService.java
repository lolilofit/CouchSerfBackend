package main.service;

import main.tables.Advert;
import main.tables.User;

import java.util.List;

public interface AdvertService {
    List<Advert> getAllAdverts();

    Advert addNewAdvert(Advert advert, User owner);
}
