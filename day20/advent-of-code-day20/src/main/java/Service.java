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
                for (char c : line.toCharArray()) {
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
                    startCord = new Cord(x, y);
                }
                if (board.get(y).get(x) == 'E') {
                    endCord = new Cord(x, y);
                }
            }
        }
    }

    public long partOne() {
        List<List<Integer>> distances = findDistances();
        return countShorterWays(distances);
    }

    private List<List<Integer>> findDistances() {
        List<List<Integer>> distances = new ArrayList<>();
        fillDistances(distances);
        distances.get(startCord.getPosY()).set(startCord.getPosX(), 0);

        Cord defCord = startCord;

        while (!defCord.equals(endCord)) {
            for (Cord direction : directions) {
                int nextPosX = defCord.getPosX() + direction.getPosX();
                int nextPosY = defCord.getPosY() + direction.getPosY();

                if (!(nextPosX < 0 || nextPosY < 0 || nextPosX >= maxX || nextPosY >= maxY)) {
                    Cord nextCord = new Cord(nextPosX, nextPosY);
                    if (!(board.get(nextPosY).get(nextPosX) == '#' || distances.get(nextPosY).get(nextPosX) != -1)) {
                        distances.get(nextPosY).set(nextPosX, distances.get(defCord.getPosY()).get(defCord.getPosX()) + 1);
                        defCord = nextCord;
                    }
                }
            }
        }
        return distances;
    }

    private void fillDistances(List<List<Integer>> distances) {
        for (int x = 0; x < maxX; x++) {
            List<Integer> row = new ArrayList<>();
            for (int y = 0; y < maxY; y++) {
                row.add(-1);
            }
            distances.add(row);
        }
    }

    private int countShorterWays(List<List<Integer>> distances) {
        int counter = 0;
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (board.get(y).get(x) != '#') {
                    for (Cord defCord: List.of(new Cord(x + 2,y), new Cord(x + 1,y + 1), new Cord(x,y + 2), new Cord(x - 1,y - 1))) {
                        int defPosX = defCord.getPosX();
                        int defPosY = defCord.getPosY();
                        if (!( defPosX < 0 || defPosY < 0 || defPosX >= maxX || defPosY >= maxY)) {
                            if (board.get(defPosY).get(defPosX) != '#') {
                                if (Math.abs(distances.get(y).get(x) - distances.get(defPosY).get(defPosX)) >= 102) {
                                    counter++;
                                }
                            }
                        }
                    }
                }

            }
        }
        return counter;
    }

    public int partTwo() {
        List<List<Integer>> distances = findDistances();
        return countShorterWays2(distances);
        //978910 low
        //1110241 high
    }
    private int countShorterWays2(List<List<Integer>> distances) {
        int counter = 0;
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (board.get(y).get(x) != '#') {
                    for (int radius = 2; radius < 21; radius++) {
                        for (int dr = 0; dr < radius + 1; dr++) {
                            int dc = radius - dr;

                            for (Cord defCord : List.of(new Cord(x + dr, y + dc), new Cord(x + dr, y - dc), new Cord(x - dr, y + dc), new Cord(x - dr, y - dc))) {
                                int defPosX = defCord.getPosX();
                                int defPosY = defCord.getPosY();
                                if (!(defPosX < 0 || defPosY < 0 || defPosX >= maxX || defPosY >= maxY)) {
                                    if (board.get(defPosY).get(defPosX) != '#') {
                                        if (distances.get(y).get(x) - distances.get(defPosY).get(defPosX) >= 100 + radius) {
                                            counter++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }
}
