package model;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue
    private Integer playerId;

    @Column(nullable=false)
    private String playerName;

    @Column(nullable=false)
    private int gameTime;

    @Column(nullable=false)
    private int score;

    public Player() {}

    public Player(String playerName, int gameTime, int score) {
        this.playerName = playerName;
        this.gameTime = gameTime;
        this.score = score;
    }

    public String getPlayerName() { return playerName; }

    public int getGameTime() { return gameTime; }

    public int getScore() { return score; }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
