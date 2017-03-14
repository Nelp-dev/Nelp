package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Participant;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
