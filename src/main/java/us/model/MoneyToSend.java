package us.model;

import javax.persistence.*;

@Entity
public class MoneyToSend {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="money_to_send")
    private int amount;
    @OneToOne
    @JoinColumn(name="giver_id")
    private User giver;
    @OneToOne
    @JoinColumn(name="recipient_id")
    private User recipient;
    @OneToOne
    @JoinColumn(name="meeting_id")
    private Meeting meeting;

    public MoneyToSend() { }
    public MoneyToSend(int amount, User giver, User recipient, Meeting meeting) {
        this.amount = amount;
        this.giver = giver;
        this.recipient = recipient;
        this.meeting = meeting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int money) {
        this.amount = money;
    }

    public User getGiver() {
        return giver;
    }

    public void setGiver(User giver) {
        this.giver = giver;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
