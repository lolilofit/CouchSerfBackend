package main.rm.security.data;


import main.rm.security.data.types.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface JpaRoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByRoleName(String name);
}
