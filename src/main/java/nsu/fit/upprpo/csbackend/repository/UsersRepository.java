package nsu.fit.upprpo.csbackend.repository;

import nsu.fit.upprpo.csbackend.tables.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    User getUserByUserid(Long userid);
    User findUserByUsername(String username);
}
