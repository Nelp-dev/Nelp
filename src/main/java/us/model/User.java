package us.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @Column(name="ssoId")
    private String ssoId;
    @Column(name="name")
    private String name;
    @Column(name="account_bank")
    private String account_bank;
    @Column(name="account_number")
    private String account_number;
    @Column(name="password")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Participation> participationList = new ArrayList<>();

    public User() {}

    public User(String ssoId, String name, String account_number, String password) {
        this.ssoId = ssoId;
        this.name = name;
        this.account_number = account_number;
        this.password = password;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getName() {
        return name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }
}
