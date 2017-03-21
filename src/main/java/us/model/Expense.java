package us.model;

import javax.persistence.*;

@Entity
public class Expense {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="amount")
    private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Expense() {
    }

}
