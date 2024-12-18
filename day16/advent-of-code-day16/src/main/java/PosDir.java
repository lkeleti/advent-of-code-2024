import java.util.Objects;

public class PosDir {
    private Cord position;
    private Cord direction;

    public PosDir(Cord position, Cord direction) {
        this.position = position;
        this.direction = direction;
    }

    public Cord getPosition() {
        return position;
    }

    public void setPosition(Cord position) {
        this.position = position;
    }

    public Cord getDirection() {
        return direction;
    }

    public void setDirection(Cord direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PosDir posDir = (PosDir) o;
        return Objects.equals(position, posDir.position) && Objects.equals(direction, posDir.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }
}
