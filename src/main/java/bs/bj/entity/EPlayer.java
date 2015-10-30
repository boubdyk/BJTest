package bs.bj.entity;


import javax.persistence.*;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Entity
@Table(name = "Player")
public class EPlayer {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "balance")
    private Long balance;

    public EPlayer(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
