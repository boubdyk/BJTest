package bs.bj.entity;

import javax.persistence.*;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Entity
@Table(name = "HISTORY")
public class EHistory {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "action_id")
    private Integer actionId;

    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "card_id")
    private Integer cardId;

    @Column(name = "bet")
    private Integer bet;

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "card_weight")
    private Integer cardWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private EGame eGame;
    

    public EHistory(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getCardWeight() {
        return cardWeight;
    }

    public void setCardWeight(Integer cardWeight) {
        this.cardWeight = cardWeight;
    }

    public EGame geteGame() {
        return eGame;
    }

    public void seteGame(EGame eGame) {
        this.eGame = eGame;
    }
}
