package us.model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String location;
    private String time;
    private String url;
    @OneToMany
    @JoinColumn(name="meeting_id")
    private List<Participant> participantList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Meeting() {
    }

    public Meeting(String name, String location, String time) {
        this.name = name;
        this.location = location;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
