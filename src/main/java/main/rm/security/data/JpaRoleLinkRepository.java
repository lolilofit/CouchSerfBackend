package main.rm.security.data;


import main.rm.security.data.types.SecuredUserRole;
import org.springframework.data.repository.CrudRepository;

public interface JpaRoleLinkRepository extends CrudRepository<SecuredUserRole, Integer> {

}
