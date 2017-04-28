package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.MoneyToSend;
import us.model.Payment;
import us.model.User;

import java.util.List;

public interface MoneyToSendRepository extends CrudRepository<MoneyToSend, Integer> {
    List<MoneyToSend> findByPayment(Payment payment);
    MoneyToSend findBySender(User sender);
}
