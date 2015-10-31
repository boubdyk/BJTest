package bs.bj.entity;


import javax.persistence.*;
import java.util.List;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Entity
@Table(name = "Player")
public class EPlayer {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "balance")
    private Integer balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ePlayer")
    private List<EGame> eGames;

    public EPlayer(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public List<EGame> geteGames() {
        return eGames;
    }

    public void seteGames(List<EGame> eGames) {
        this.eGames = eGames;
    }
}
