package domain;

public class BoardBuilder {
    private int startFlag;
    private int noOfPlayers;
    private int currentPlayerTurn;
    private int shutDownFlag;

    public BoardBuilder withStartFlag(int startFlag) {
        this.startFlag = startFlag;
        return this;
    }

    public BoardBuilder withNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
        return this;
    }

    public BoardBuilder withCurrentPlayerTurn(int currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
        return this;
    }

    public BoardBuilder withShutDownFlag(int shutDownFlag) {
        this.shutDownFlag = shutDownFlag;
        return this;
    }

    public int getStartFlag() {
        return startFlag;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public int getShutDownFlag() {
        return shutDownFlag;
    }

    public Board build() {
        return new Board(this);
    }
}
