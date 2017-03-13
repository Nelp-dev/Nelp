package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Participant;

/**
 * Created by PrinceY on 2017-03-13.
 */
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
