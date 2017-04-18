package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Meeting;
import us.model.MoneyToSend;
import us.model.User;

import java.util.List;

public interface MoneyToSendRepository extends CrudRepository<MoneyToSend, Integer> {
    List<MoneyToSend> findByMeeting(Meeting meeting);
    MoneyToSend findBySender(User user);
}
