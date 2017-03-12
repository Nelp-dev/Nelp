package us.model;

import javax.persistence.*;

@Entity
public class Participant {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="meeting_id")
    private int meetingId;

    @Column(name="name")
    private String name;

    public String getName() {
        return name;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participant() {
    }

    public Participant(String name) {
        this.name = name;
    }
}
