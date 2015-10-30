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
    private Long id;

    @Column(name = "action_id")
    private Long actionId;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "bet")
    private Long bet;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "card_weight")
    private Long cardWeight;

    public EHistory(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getBet() {
        return bet;
    }

    public void setBet(Long bet) {
        this.bet = bet;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getCardWeight() {
        return cardWeight;
    }

    public void setCardWeight(Long cardWeight) {
        this.cardWeight = cardWeight;
    }
}
