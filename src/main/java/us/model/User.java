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
    @Column(name="account_number")
    private String account_number;
    @Column(name="password")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Participation> participationList = new ArrayList<>();

    public User() {}

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
    
}
