package us.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@IdClass(ParticipationId.class)
public class Participation {
    @Id
    @ManyToOne
    @JoinColumn(name="meeting_id")
    private Meeting meeting;
    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy="participation")
    private List<Payment> paymentList = new ArrayList<>();

    public Participation() { }

    public Participation(Meeting meeting, User user) {
        this.meeting = meeting;
        this.user = user;

        meeting.addParticipation(this);
        user.addParticipation(this);
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public void leave(Meeting meeting, User user) {
        meeting.removeParticipation(this);
        user.removeParticipation(this);
    }

    public User getUser() {
        return user;
    }

    public void addPayment(Payment payment) {
        this.paymentList.add(payment);
    }
}