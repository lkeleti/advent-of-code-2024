import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public final List<List<Integer>> board = new ArrayList<>();
    public final List<Cord> starts = new ArrayList<>();
    public int maxX = -1;
    public int maxY = -1;

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> row = new ArrayList<>();
                for (char c: line.toCharArray()) {
                    row.add(c-'0');
                }
                board.add(row);
            }
            maxX = board.size();
            maxY = board.getFirst().size();
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        findStarts();
        return 0;
    }

    private void findStarts() {
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (board.get(i).get(j) == 0) {
                    starts.add(new Cord(i,j));
                }
            }
        }
    }

    public int partTwo() {
        return 0;
    }
}
