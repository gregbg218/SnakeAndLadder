package domain;

public class SnakeLadderBuilder {
    private int head;
    private int tail;

    public SnakeLadderBuilder witHead(int head) {
        this.head = head;
        return this;
    }

    public SnakeLadderBuilder withTail(int tail) {
        this.tail = tail;
        return this;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }


    public SnakeLadder build() {
        return new SnakeLadder(this);
    }
}
