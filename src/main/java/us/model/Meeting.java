package us.model;

import org.springframework.beans.factory.annotation.Autowired;
import us.repository.UserRepository;

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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "meeting_has_user", joinColumns = @JoinColumn(name = "meeting_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> userList = new ArrayList<User>();

    public int getId() {
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user){
        this.userList.add(user); }

    public boolean isContainUser(User user){
        for(User foundUser : userList) {
            if(foundUser.getId() == user.getId())
                return true;
        }
        return false;
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

    public void removeUser(User user) {
        userList.remove(user);
    }
}
