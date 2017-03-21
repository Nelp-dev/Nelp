package us.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @Column(name="id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="account_number")
    private String account_number;
    @Column(name="password")
    private String password;
    @ManyToMany(mappedBy = "userList")
    private List<Meeting> meetingList = new ArrayList<Meeting>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMeetingList(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    public void addMeeting(Meeting meeting){ this.meetingList.add(meeting); }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public void leaveMeeting(Meeting meeting) {
        meetingList.remove(meeting);
    }
}
