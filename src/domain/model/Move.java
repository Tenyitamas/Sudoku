package domain.model;

import java.io.Serializable;

public class Move implements Serializable {
    private final int x,y,oldValue, newValue;

    public Move(int x, int y, int oldValue, int newValue) {
        this.x = x;
        this.y = y;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }
}
