package main.rm.security.data;

import main.rm.security.data.types.SecuredUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaSecuredUserRepository extends CrudRepository<SecuredUser, String> {
    //Optional<SecuredUser> findByUsername(String username);
    List<SecuredUser> findByUsername(String username);
}
