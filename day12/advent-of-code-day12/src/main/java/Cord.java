import java.util.Objects;

public class Cord {
    private double posX;
    private double posY;

    public Cord(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cord cord)) return false;
        return getPosX() == cord.getPosX() && getPosY() == cord.getPosY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosX(), getPosY());
    }

    @Override
    public String toString() {
        return "(" +
                posX +
                ", " +
                posY +
                ')';
    }
}
