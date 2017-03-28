package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Meeting;
import us.model.Participation;
import us.model.ParticipationId;

import java.util.List;

public interface ParticipationRepository extends CrudRepository<Participation, ParticipationId> {
    List<Participation> findByMeeting(Meeting meeting);
}
