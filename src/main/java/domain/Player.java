package domain;

import java.io.Serializable;

public class Player implements Serializable, Comparable<Player> {
    private int currentPosition;
    private int playerNo;

    public Player(PlayerBuilder playerBuilder) {
        this.currentPosition = playerBuilder.getCurrentPosition();
        this.playerNo = playerBuilder.getPlayerNo();
    }

    @Override
    public int compareTo(Player player) {
        if (this.currentPosition == player.currentPosition)
            return 0;
        else
            return this.currentPosition > player.currentPosition ? -1 : 1;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }
}
