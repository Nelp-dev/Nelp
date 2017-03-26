package us.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jihun on 2017. 3. 26..
 */
@Entity
@IdClass(ParticipationId.class)
public class Participation{
//    @Id
//    @Embedded
//    private ParticipationId id;

    @Id
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting_id;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
}