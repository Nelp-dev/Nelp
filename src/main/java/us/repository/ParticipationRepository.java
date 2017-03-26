package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Participation;
import us.model.ParticipationId;

/**
 * Created by jihun on 2017. 3. 27..
 */
public interface ParticipationRepository extends CrudRepository<Participation, ParticipationId> {

}
