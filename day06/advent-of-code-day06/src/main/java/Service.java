import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public final List<List<Character>> board = new ArrayList<>();
    public Cord startCord = new Cord(-1,-1);
    public Cord maxCord = new Cord(-1,-1);

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char c:line.toCharArray()) {
                    row.add(c);
                }
                board.add(row);
            }
            findStartMax();
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }

    private void findStartMax() {
        maxCord.setPosX(board.size());
        maxCord.setPosY(board.getFirst().size());
        for (int i = 0; i < maxCord.getPosX(); i++) {
            for (int j = 0; j < maxCord.getPosY(); j++) {
                if (board.get(j).get(i) == '^') {
                    startCord.setPosX(i);
                    startCord.setPosY(j);
                    break;
                }
            }
        }
    }

    public int partOne() {
        int defX = startCord.getPosX();
        int defY = startCord.getPosY();
        Directions defDirection = Directions.UP;
        int counter = 1;
        while (!(defX < 0 || defX >= maxCord.getPosX() || defY < 0 || defY >= maxCord.getPosY())) {
            if (board.get(defY).get(defX) == '|' || board.get(defY).get(defX) == '|') {
                board.get(defY).set(defX, '+');
            } else {
                if (defDirection == Directions.LEFT || defDirection == Directions.RIGHT) {
                    board.get(defY).set(defX, '-');
                } else {
                    board.get(defY).set(defX, '|');
                }
            }
            int nextX = defX + defDirection.getDirectionValue().getPosX();
            int nextY = defY + defDirection.getDirectionValue().getPosY();
            if (!(nextX < 0 ||
                    nextX >= maxCord.getPosX() ||
                    nextY < 0 ||
                    nextY >= maxCord.getPosY())) {
                if (board.get(nextY).get(nextX) == '#') {
                    defDirection = defDirection.getNextDirection(defDirection);
                    nextX = defX + defDirection.getDirectionValue().getPosX();
                    nextY = defY + defDirection.getDirectionValue().getPosY();
                }
            }
            defX = nextX;
            defY = nextY;
            counter++;
        }
        return countX();
    }

    private int countX() {
        int counter = 0;
        for (int i = 0; i < maxCord.getPosX(); i++) {
            for (int j = 0; j < maxCord.getPosY(); j++) {
                if (board.get(j).get(i) == '-' ||
                        board.get(j).get(i) == '|' ||
                        board.get(j).get(i) == '+') {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int partTwo() {
        return 0;
    }
}
