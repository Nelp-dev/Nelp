package us.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meeting {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="location")
    private String location;
    @Column(name="time")
    private String time;
    @Column(name="url")
    private String url;
    @OneToMany(mappedBy="meeting")
    private List<Participation> participationList = new ArrayList<>();


    public Meeting() {
    }

    public Meeting(String name, String location, String time) {
        this.name = name;
        this.location = location;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl() {
        String base_url = "http://localhost:8080/meetings/";
        this.url = base_url + Integer.toString(this.id);
    }

    public List<Participation> getParticipationList() {
        return participationList;
    }

    public void setParticipationList(List<Participation> participationList) {
        this.participationList = participationList;
    }

    public void addParticipation(Participation participation) {
        this.participationList.add(participation);
    }

    public void removeParticipation(Participation participation) { this.participationList.remove(participation); }

    public boolean isParticipant(User user){
        for(Participation participation : participationList) {
            if(participation.getUser().getSsoId().equals(user.getSsoId()))
                return true;
        }
        return false;
    }
}
