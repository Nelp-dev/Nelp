package us.model;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="meeting_id"),
            @JoinColumn(name="user_id")
    })
    private Participation participation;
    private String name;
    private int amount;

    public Payment() {}
}
