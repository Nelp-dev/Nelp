package us.model;

import javax.persistence.*;

@Entity
@IdClass(ParticipationId.class)
public class Participation{
    @Id
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Participation() { }

    public Participation(Meeting meeting, User user) {
        this.meeting = meeting;
        this.user = user;

        meeting.addParticipation(this);
        user.addParticipation(this);
    }

    public void leave(Meeting meeting, User user) {
        meeting.removeParticipation(this);
        user.removeParticipation(this);
    }

    public User getUser() {
        return user;
    }
}