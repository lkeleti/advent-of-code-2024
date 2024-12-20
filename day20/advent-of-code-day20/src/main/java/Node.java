import java.util.Objects;

public class Node implements Comparable{
    private int cost;
    private Cord position;

    public Node(int cost, Cord position) {
        this.cost = cost;
        this.position = position;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node node)) return false;
        return getCost() == node.getCost() && Objects.equals(getPosition(), node.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getPosition());
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Node node)) return 0;
        return cost - node.getCost();
    }
}
