package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findBySsoId(String sso_id);
}
