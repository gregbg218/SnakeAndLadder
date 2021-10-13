package domain;

public class SnakeLadderFactory {

    public static SnakeLadder getSnakeLadder(String type, int head, int tail) {
        if (type.equals("snake")) {
            SnakeLadder snake = (new SnakeLadderBuilder()).witHead(head).withTail(tail).build();
            return snake;

        } else {
            SnakeLadder ladder = (new SnakeLadderBuilder()).witHead(head).withTail(tail).build();
            return ladder;
        }

    }

}
