import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public final List<List<Character>> board = new ArrayList<>();
    public int startX;
    public int startY;
    public int maxX;
    public int maxY;
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
        maxX = board.size();
        maxY = board.getFirst().size();
        for (int i = 0; i < maxX;i++) {
            for (int j = 0; j < maxY; j++) {
                if (board.get(j).get(i) == '^') {
                    startX = i;
                    startY = j;
                    break;
                }
            }
        }
    }

    public long partOne() {
        int defX = startX;
        int defY = startY;
        int counter = 0;
        while (!(defX < 0 || defX > maxX || defY < 0 || defY > startY)) {
            if (board.get(defY-1).get(defX) != '#') {
                defY -=1;
            } else {
                defX += 1;
            }
            counter++;
        }
        return counter;
    }
    public int partTwo() {
        return 0;
    }
}
