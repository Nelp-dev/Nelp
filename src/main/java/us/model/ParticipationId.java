package us.model;

import java.io.Serializable;

public class ParticipationId implements Serializable {
    private int meeting;
    private String user;

    public ParticipationId() { }
    public ParticipationId(int meeting, String user) {
        this.meeting = meeting;
        this.user = user;
    }
}
