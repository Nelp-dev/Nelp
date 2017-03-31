package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
}
