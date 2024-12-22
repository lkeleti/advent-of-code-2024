import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private List<Character> direction;
    private Cord position;

    public Node(List<Character> direction, Cord position) {
        this.direction = direction;
        this.position = position;
    }

    public List<Character> getDirection() {
        return direction;
    }

    public void setDirection(List<Character> direction) {
        this.direction = direction;
    }

    public Cord getPosition() {
        return position;
    }

    public void setPosition(Cord position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node node)) return false;
        return Objects.equals(getDirection(), node.getDirection()) && Objects.equals(getPosition(), node.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDirection(), getPosition());
    }

    @Override
    public String toString() {
        return "Node{" +
                "direction=" + direction +
                ", position=" + position +
                '}';
    }

    public List<Character> copyDirections() {
        List<Character> copyDirections = new ArrayList<>();
        for (Character c: direction) {
            copyDirections.add(c);
        }
        return copyDirections;
    }
}
