package domain;

public class PlayerBuilder {

    private int currentPosition;
    private int playerNo;

    public PlayerBuilder withCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        return this;
    }

    public PlayerBuilder withPlayerNo(int playerNo) {
        this.playerNo = playerNo;
        return this;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public Player build() {
        return new Player(this);
    }
}
