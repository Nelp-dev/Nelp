package us.model;

import java.io.Serializable;

public class ParticipationId implements Serializable {
    private int meeting;
    private int user;

    public ParticipationId() { }
    public ParticipationId(int meeting, int user) {
        this.meeting = meeting;
        this.user = user;
    }
}
