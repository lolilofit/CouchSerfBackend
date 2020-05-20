package main.rm.security.data;

import main.rm.security.data.types.SecuredUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaSecuredUserRepository extends CrudRepository<SecuredUser, String> {
    List<SecuredUser> findByUsername(String username);
}
