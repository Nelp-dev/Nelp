package us.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by jihun on 2017. 3. 26..
 */
//@Embeddable
public class ParticipationId implements Serializable{
//    @ManyToOne
//    @JoinColumn(name = "meeting")
//    private Meeting meeting;
//    @ManyToOne
//    @JoinColumn(name = "user")
//    private User user;
    private int meeting_id;
    private int user_id;
}

