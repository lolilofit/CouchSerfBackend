package nsu.fit.upprpo.csbackend.security.data;


import nsu.fit.upprpo.csbackend.security.data.types.SecuredUserRole;
import org.springframework.data.repository.CrudRepository;

public interface JpaRoleLinkRepository extends CrudRepository<SecuredUserRole, Integer> {

}
