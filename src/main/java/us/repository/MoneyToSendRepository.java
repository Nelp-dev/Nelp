package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.MoneyToSend;

public interface MoneyToSendRepository extends CrudRepository<MoneyToSend, Integer> {
}
