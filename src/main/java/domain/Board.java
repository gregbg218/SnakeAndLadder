package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Random;

public class Board implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(Board.class);
    public ArrayList<Player> players;
    public LinkedHashMap<Integer, SnakeLadder> snakes;
    public LinkedHashMap<Integer, SnakeLadder> ladders;
    private int startFlag;
    private int noOfPlayers;
    private int currentPlayerTurn;
    private int shutDownFlag;

    public Board(BoardBuilder boardBuilder) {
        this.startFlag = boardBuilder.getStartFlag();
        this.noOfPlayers = boardBuilder.getNoOfPlayers();
        this.currentPlayerTurn = boardBuilder.getCurrentPlayerTurn();
        this.shutDownFlag = boardBuilder.getShutDownFlag();

        players = new ArrayList<Player>();
        snakes = new LinkedHashMap<Integer, SnakeLadder>();
        ladders = new LinkedHashMap<Integer, SnakeLadder>();
    }

    public Board deSerialize(String serializedFileName) throws IOException, FileNotFoundException, ClassNotFoundException {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializedFileName));
            Board board = (Board) in.readObject();
            logger.info("Starting previous game");
            return board;
        } catch (Exception e) {
            return null;
        }
    }

    public void enrollPlayers() {
        for (int i = 0; i < noOfPlayers; i++) {
            players.add((new PlayerBuilder()).withPlayerNo(i).withCurrentPosition(0).build());
        }
    }

    public void addPlayer() {
        noOfPlayers = noOfPlayers + 1;
        players.add((new PlayerBuilder()).withPlayerNo(noOfPlayers - 1).withCurrentPosition(0).build());

    }

    public void showPlayerPositions() {
        for (int i = 0; i < noOfPlayers; i++) {
            System.out.println("Player no: " + (i + 1) + " Position: " + (players.get(i)).getCurrentPosition());
        }

    }

    public void placeSnakesAndLadders() {
        snakes.put(27, SnakeLadderFactory.getSnakeLadder("snake", 27, 5));
        snakes.put(40, SnakeLadderFactory.getSnakeLadder("snake", 40, 3));
        snakes.put(43, SnakeLadderFactory.getSnakeLadder("snake", 43, 18));
        snakes.put(54, SnakeLadderFactory.getSnakeLadder("snake", 54, 31));
        snakes.put(66, SnakeLadderFactory.getSnakeLadder("snake", 66, 45));
        snakes.put(76, SnakeLadderFactory.getSnakeLadder("snake", 76, 58));
        snakes.put(89, SnakeLadderFactory.getSnakeLadder("snake", 89, 54));
        snakes.put(99, SnakeLadderFactory.getSnakeLadder("snake", 99, 41));

        ladders.put(4, SnakeLadderFactory.getSnakeLadder("ladder", 25, 4));
        ladders.put(13, SnakeLadderFactory.getSnakeLadder("ladder", 46, 13));
        ladders.put(33, SnakeLadderFactory.getSnakeLadder("ladder", 49, 33));
        ladders.put(63, SnakeLadderFactory.getSnakeLadder("ladder", 42, 63));
        ladders.put(50, SnakeLadderFactory.getSnakeLadder("ladder", 69, 50));
        ladders.put(62, SnakeLadderFactory.getSnakeLadder("ladder", 81, 62));
        ladders.put(74, SnakeLadderFactory.getSnakeLadder("ladder", 92, 74));
    }

    public void showSnakesAndLadders() {
        int count = 1;
        for (int head : snakes.keySet()) {
            System.out.println("Snake no: " + count + " Head: " + head + " Tail: " + (snakes.get(head)).getTail());
            count = count + 1;
        }
        System.out.println();

        count = 1;
        for (int tail : ladders.keySet()) {
            System.out.println("Ladder no: " + count + " Tail: " + tail + " Head: " + (ladders.get(tail)).getHead());
            count = count + 1;
        }

    }

    public void rollDice() {
        int diceNo = ((new Random()).nextInt(6)) + 1;
        int position = (players.get(currentPlayerTurn)).getCurrentPosition();

        System.out.println("Player no: " + (currentPlayerTurn + 1));
        logger.info("Player no: " + (currentPlayerTurn + 1));
        System.out.println("Current position is " + position);
        System.out.println("You rolled a " + diceNo);
        logger.info("Number on rolling dice " + diceNo);
        System.out.println("You position will be " + position + "+" + diceNo);

        players.get(currentPlayerTurn).setCurrentPosition(position + diceNo);
        this.checkGreaterThan100(diceNo);
        this.checkSnake(position + diceNo);
        this.checkLadder(position + diceNo);
        System.out.println("New Position: " + (players.get(currentPlayerTurn)).getCurrentPosition());
        this.checkWinner();
    }

    public void checkWinner() {
        if (players.get(currentPlayerTurn).getCurrentPosition() == 100) {
            System.out.println("Player number " + (currentPlayerTurn + 1) + " is the winner");
            logger.info("Player number " + (currentPlayerTurn + 1) + " is the winner");
            try {
                shutDown("snakesAndLadders.dat");
            } catch (Exception e) {
                System.out.println(e);
                logger.warn("Exception found " + e.toString());
                logger.info("Could not save game");
            }
        }
    }

    public void checkGreaterThan100(int diceNo) {
        if (players.get(currentPlayerTurn).getCurrentPosition() > 100 && players.get(currentPlayerTurn).getCurrentPosition() != 100) {
            int newPosition = players.get(currentPlayerTurn).getCurrentPosition() - diceNo;
            players.get(currentPlayerTurn).setCurrentPosition(newPosition);
            System.out.println("Sorry you cant go over 100 roll again next time");
            logger.info("Sorry you cant go over 100 roll again next time");
        }
    }

    public void checkSnake(int position) {
        if (snakes.containsKey(position)) {
            int newPosition = snakes.get(position).getTail();
            players.get(currentPlayerTurn).setCurrentPosition(newPosition);
            System.out.println("Oh no you landed over a snake you will be demoted to position " + newPosition);
            logger.info("Oh no you landed over a snake you will be demoted to position " + newPosition);
        }
    }

    public void checkLadder(int position) {
        if (ladders.containsKey(position)) {
            int newPosition = ladders.get(position).getHead();
            players.get(currentPlayerTurn).setCurrentPosition(newPosition);
            System.out.println("Great you landed over a ladder you will be promoted to position " + newPosition);
            logger.info("Great you landed over a ladder you will be promoted to position " + newPosition);
        }
    }

    public void shutDown(String serializedFileName) throws IOException, FileNotFoundException {
        System.out.println("Game Over shutting down");
        logger.info("Game Over shutting down");
        shutDownFlag = 1;
        serialize(serializedFileName);
    }

    public void serialize(String serializedFileName) throws IOException, FileNotFoundException {
        ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(new File(serializedFileName)));
        objout.writeObject(this);
        objout.close();
    }

    public void showRanking() {
        ArrayList<Player> rankList = new ArrayList<Player>(players);
        Collections.sort(rankList);
        for (int i = 0; i < rankList.size(); i++) {
            System.out.println("Rank " + (i + 1) + " Player No: " + ((rankList.get(i).getPlayerNo() + 1) + " at position: " + rankList.get(i).getCurrentPosition()));
        }
    }

    public int getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(int startFlag) {
        this.startFlag = startFlag;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(int currentPlayerTurn) {
        if (currentPlayerTurn >= noOfPlayers)
            this.currentPlayerTurn = 0;
        else
            this.currentPlayerTurn = currentPlayerTurn;
    }

    public int getShutDownFlag() {
        return shutDownFlag;
    }

    public void setShutDownFlag(int shutDownFlag) {
        this.shutDownFlag = shutDownFlag;
    }

}

