package domain;

import java.io.Serializable;

public class SnakeLadder implements Serializable {
    private int head;
    private int tail;

    public SnakeLadder(SnakeLadderBuilder snakeLadderBuilder) {
        this.head = snakeLadderBuilder.getHead();
        this.tail = snakeLadderBuilder.getTail();
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getTail() {
        return tail;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }
}
