package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.User;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {
    User findById(int id);
}
