package nsu.fit.upprpo.csbackend.repository;

import nsu.fit.upprpo.csbackend.tables.Advert;
import org.springframework.data.repository.CrudRepository;

public interface AdvertRepository extends CrudRepository<Advert, Long> {
    Advert findByAdId(Long adId);
}
