import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private final List<List<Character>> board = new ArrayList<>();
    private int maxX = -1;
    private int maxY = -1;

    private final Cord up = new Cord(0, -1);
    private final Cord right = new Cord(1, 0);
    private final Cord down = new Cord(0, 1);
    private final Cord left = new Cord(-1, 0);

    private final List<Cord> directions = List.of(up, right, down, left);

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
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Node> seen = new TreeSet<>();
        //seen.add(new Node(start,right));
        pq.add(new Node(0, start,right));

        while (!pq.isEmpty()) {
            Node defNode = pq.poll();
            int cost = defNode.getCost();
            seen.add(defNode);
            List<Node> nextNodes = List.of(new Node(cost + 1,
                            new Cord(defNode.getPosition().getPosX() + defNode.getDirection().getPosX(),
                                    defNode.getPosition().getPosY() + defNode.getDirection().getPosY()),
                            new Cord(defNode.getDirection().getPosX(), defNode.getDirection().getPosY())),
                    new Node(cost + 1000,
                            new Cord(defNode.getPosition().getPosX(),
                            defNode.getPosition().getPosY()),
                            new Cord(defNode.getDirection().getPosY(), -defNode.getDirection().getPosX())),
                    new Node(cost + 1000,
                            new Cord(defNode.getPosition().getPosX(),
                                    defNode.getPosition().getPosY()),
                            new Cord(-defNode.getDirection().getPosY(), defNode.getDirection().getPosX())));
            if (board.get(defNode.getPosition().getPosY()).get(defNode.getPosition().getPosX()) == 'E') {
                System.out.println(cost);
                break;
            }
            for (Node newNode: nextNodes) {
                if (!seen.contains(newNode)) {
                    if (board.get(newNode.getPosition().getPosY()).get(newNode.getPosition().getPosX()) != '#') {
                        pq.add(newNode);
                    }
                }
            }
        }

        return 0;
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
