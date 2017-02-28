package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Meeting;

import java.util.List;

/**
 * Created by jihun on 2017. 2. 28..
 */
public interface MeetingRepository extends CrudRepository<Meeting, Long>{

}
