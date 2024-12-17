import java.util.Objects;

public class Node implements Comparable<Node>{
    private int cost;
    private Cord position;
    private Cord direction;

    public Node(Cord position, Cord direction) {
        this.cost = -1;
        this.position = position;
        this.direction = direction;
    }

    public Node(int cost, Cord position, Cord direction) {
        this.cost = cost;
        this.position = position;
        this.direction = direction;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
        if (!(o instanceof Node node)) return false;
        return getCost() == node.getCost() && Objects.equals(getPosition(), node.getPosition()) && Objects.equals(getDirection(), node.getDirection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getPosition(), getDirection());
    }

    @Override
    public int compareTo(Node o) {
        return this.cost - o .getCost();
    }

    @Override
    public String toString() {
        return "[" +
                + cost +
                ", " + position +
                ", " + direction +
                ']';
    }
}
