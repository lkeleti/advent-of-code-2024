import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private final List<List<Character>> board = new ArrayList<>();
    private int maxX = -1;
    private int maxY = -1;

    private final Cord right = new Cord(1, 0);

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char c: line.toCharArray()) {
                    row.add(c);
                }
                board.add(row);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }

        maxX = board.getFirst().size();
        maxY = board.size();
    }
    public long partOne() {
        Cord start = findStart();
        Cord end = findEnd();
        long cost = -1;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        List<PosDir> seen = new ArrayList<>();
        seen.add(new PosDir(start,right));
        pq.add(new Node(0, start,right));

        while (!pq.isEmpty()) {
            Node defNode = pq.poll();
            cost = defNode.getCost();
            if (board.get(defNode.getPosition().getPosY()).get(defNode.getPosition().getPosX()) == 'E') {
                break;
            }

            Node goForward = new Node(cost + 1,
                    new Cord(defNode.getPosition().getPosX() + defNode.getDirection().getPosX(),
                            defNode.getPosition().getPosY() + defNode.getDirection().getPosY()),
                    new Cord(defNode.getDirection().getPosX(), defNode.getDirection().getPosY()));
            Node goLeft = new Node(cost + 1000,
                    new Cord(defNode.getPosition().getPosX(),
                            defNode.getPosition().getPosY()),
                    new Cord(defNode.getDirection().getPosY(), -1 * defNode.getDirection().getPosX()));
            Node goRight = new Node(cost + 1000,
                    new Cord(defNode.getPosition().getPosX(),
                            defNode.getPosition().getPosY()),
                    new Cord(-1 * defNode.getDirection().getPosY(), defNode.getDirection().getPosX()));
            List<Node> nextNodes = new ArrayList<>();
            nextNodes.add(goForward);
            nextNodes.add(goLeft);
            nextNodes.add(goRight);

            for (Node newNode : nextNodes) {
                if (!(seen.contains(new PosDir(newNode.getPosition(), newNode.getDirection())) || board.get(newNode.getPosition().getPosY()).get(newNode.getPosition().getPosX()) == '#')) {
                    pq.add(newNode.copy());
                    seen.add(new PosDir(newNode.getPosition(), newNode.getDirection()));
                }
            }
        }
        return cost;
    }

    private Cord findEnd() {
        return findSomething('E');
    }

    private Cord findStart() {
        return findSomething('S');
    }

    private Cord findSomething(char whatToFind) {
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (board.get(y).get(x) == whatToFind) {
                    return new Cord(x, y);
                }
            }
        }
        return null;
    }

    public int partTwo() {
        return 0;
    }
}
