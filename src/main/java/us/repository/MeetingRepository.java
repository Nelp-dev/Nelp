package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Meeting;

public interface MeetingRepository extends CrudRepository<Meeting, Integer>{

}