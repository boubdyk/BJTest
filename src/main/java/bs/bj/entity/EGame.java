package bs.bj.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Entity
@Table(name = "GAME")
public class EGame {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_finish")
    private Date dateFinish;

    @Column(name = "winner_id")
    private Long winnerId;

    @Column(name = "player_score")
    private Integer playerScore;

    @Column(name = "dealer_score")
    private Integer dealerScore;

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "eGame")
    private List<EHistory> history;

    @ManyToOne
    private EPlayer ePlayer;

    public EGame(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public void setDealerScore(int dealerScore) {
        this.dealerScore = dealerScore;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
