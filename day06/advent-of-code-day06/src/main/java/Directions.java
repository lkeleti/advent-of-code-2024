public enum Directions {
    UP(new Cord(0,-1)),
    RIGHT(new Cord(1,0)),
    DOWN(new Cord(0,1)),
    LEFT(new Cord(-1,0));

    private final Cord directionValue;

    Directions(Cord directionValue) {
        this.directionValue = directionValue;
    }

    public Cord getDirection() {
        return directionValue;
    }

    public Directions getNextDirection(Directions defDirection) {
        if (defDirection == Directions.UP) {
            return RIGHT;
        } else if (defDirection == Directions.RIGHT) {
            return DOWN;
        } else if (defDirection == Directions.DOWN) {
            return LEFT;
        } else if (defDirection == Directions.LEFT) {
            return UP;
        }
        return UP;
    }

    public Cord getDirectionValue() {
        return directionValue;
    }
}
