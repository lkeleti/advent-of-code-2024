import java.util.Objects;

public class CordDir {
    private Cord cord;
    private Directions direction;

    public CordDir(Cord cord, Directions direction) {
        this.cord = cord;
        this.direction = direction;
    }

    public Cord getCord() {
        return cord;
    }

    public void setCord(Cord cord) {
        this.cord = cord;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CordDir cordDir)) return false;
        return this.cord.getPosX() == cordDir.cord.getPosX() && this.cord.getPosY() == cordDir.cord.getPosY() && this.direction == cordDir.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCord(), getDirection());
    }
}