import java.util.Objects;

public class Cord {
    private int posX;
    private int posY;

    public Cord(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
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
        return "Cord{" +
                "posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
