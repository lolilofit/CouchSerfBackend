package main.repository;

import main.tables.Advert;
import org.springframework.data.repository.CrudRepository;

public interface AdvertRepository extends CrudRepository<Advert, Long> {
    Advert findByAdId(Long adId);
}
