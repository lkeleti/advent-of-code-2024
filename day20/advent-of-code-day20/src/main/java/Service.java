import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private final List<List<Character>> board = new ArrayList<>();
    private Cord startCord;
    private Cord endCord;
    private int maxX = -1;
    private int maxY = -1;

    private final Cord up = new Cord(0,-1);
    private final Cord right = new Cord(1,0);
    private final Cord down = new Cord(0,1);
    private final Cord left = new Cord(-1,0);

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

        findInitState();
    }

    private void findInitState() {
        maxX = board.getFirst().size();
        maxY = board.size();
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (board.get(y).get(x) == 'S') {
                    startCord = new Cord(x,y);
                }
                if (board.get(y).get(x) == 'E') {
                    endCord = new Cord(x,y);
                }
            }
        }
    }

    public long partOne() {
        List<Cord> walls = new ArrayList<>();
        walls = findWalls(walls);
        int normalTime = bfs();
        Map<Integer, Integer> times = new HashMap<>();

        for (Cord wall: walls) {
            board.get(wall.getPosY()).set(wall.getPosX(),'.');
            int defTime = bfs();
            defTime = normalTime - defTime;

            if (!times.containsKey(defTime)) {
                times.put(defTime, 1);
            } else {
                times.put(defTime,times.get(defTime) + 1);
            }
            board.get(wall.getPosY()).set(wall.getPosX(),'#');
        }

        int result = 0;
        for (int key : times.keySet()) {
            if (key >= 100) {
                result += times.get(key);
            }
        }

        return result;
    }

    private List<Cord> findWalls(List<Cord> walls) {
        for (int x = 1; x < maxX-1; x++) {
            for (int y = 1; y < maxY-1; y++) {
                if (board.get(y).get(x) == '#') {
                    walls.add(new Cord(x,y));
                }
            }
        }
        return walls;
    }

    private int bfs() {
        Set<Cord> seen = new HashSet<>();
        seen.add(startCord);
        Queue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, startCord));

        while (!pq.isEmpty()) {
            Node defNode = pq.poll();
            int defCost = defNode.getCost();

            for (Cord direction: directions) {
                int nextPosX = defNode.getPosition().getPosX() + direction.getPosX();
                int nextPosY = defNode.getPosition().getPosY() + direction.getPosY();

                if (!(nextPosX < 0 || nextPosY < 0 || nextPosX >= maxX || nextPosY >= maxY)) {
                    Node nextNode = new Node(defCost + 1, new Cord(nextPosX, nextPosY));
                    if (nextNode.getPosition().equals(endCord)) {
                        return nextNode.getCost();
                    }
                    if (!(board.get(nextPosY).get(nextPosX) == '#' || seen.contains(nextNode.getPosition()))) {
                        pq.add(nextNode);
                        seen.add(nextNode.getPosition());
                    }
                }
            }
        }
        return -1;
    }

    public int partTwo() {
        return 0;
    }
}
