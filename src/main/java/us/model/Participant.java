package us.model;

import javax.persistence.*;

@Entity
public class Participant {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="account_number")
    private String account_number;
    @Column(name="meeting_id")
    private int meeting_id;
    @Column(name="password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Participant() {
    }

    public Participant(String name) {
        this.name = name;
    }
}
