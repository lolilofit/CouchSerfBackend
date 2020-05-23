package nsu.fit.upprpo.csbackend.service;

import nsu.fit.upprpo.csbackend.tables.Advert;
import nsu.fit.upprpo.csbackend.tables.User;

import java.util.List;

public interface AdvertService {
    List<Advert> getAllAdverts();
    Advert addNewAdvert(Advert advert, User owner);
}
