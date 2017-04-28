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
    @JoinColumn(name="sender_id")
    private User sender;
    @OneToOne
    @JoinColumn(name="recipient_id")
    private User recipient;
    @ManyToOne
    @JoinColumn(name="payment_id")
    private Payment payment;

    public MoneyToSend() { }
    public MoneyToSend(int amount, User sender, User recipient, Payment payment) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
        this.payment = payment;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
