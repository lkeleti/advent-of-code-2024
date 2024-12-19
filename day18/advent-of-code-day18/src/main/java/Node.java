import java.util.Objects;

public class Node implements Comparable{
    private int step;
    private Cord position;

    public Node(int step, Cord position) {
        this.step = step;
        this.position = position;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Cord getPosition() {
        return position;
    }

    public void setPosition(Cord position) {
        this.position = position;
    }

    public void incStep() {
        step += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return step == node.step && Objects.equals(position, node.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, position);
    }

    @Override
    public int compareTo(Object o) {
        Node node = (Node) o;
        return step-node.getStep();
    }
}
