import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    public final List<List<Character>> board = new ArrayList<>();
    public final Map<Character, List<Cord>> antennas = new HashMap<>();
    public final Set<Cord> interferencies = new HashSet<>();
    public int maxX;
    public int maxY;

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
        findAntennas();
    }

    private void findAntennas() {
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                char key = board.get(i).get(j);
                if (key !='.') {
                    if (!antennas.containsKey(key)) {
                        antennas.put(key, new ArrayList<>());
                    }
                    antennas.get(key).add(new Cord(i, j));
                }
            }
        }
    }

    public long partOne() {
        for (char c : antennas.keySet()) {
            if (antennas.get(c).size() > 1) {
                findInterference(antennas.get(c));
            }
        }
        return interferencies.size();
    }

    private void findInterference(List<Cord> cords) {
        for (int i = 0; i < cords.size()-1; i++) {
            for (int j = i + 1; j < cords.size(); j++) {
                int x1 = cords.get(i).getPosX();
                int y1 = cords.get(i).getPosY();
                int x2 = cords.get(j).getPosX();
                int y2 = cords.get(j).getPosY();
                Cord p1 = new Cord(2*x2-x1,2*y2-y1);
                Cord p2 = new Cord(2*x1-x2, 2*y1-y2);

                if (p1.getPosX() >= 0 && p1.getPosX() <maxX && p1.getPosY() >= 0 && p1.getPosY() < maxY) {
                    interferencies.add(p1);
                }
                if (p2.getPosX() >= 0 && p2.getPosX() <maxX && p2.getPosY() >= 0 && p2.getPosY() < maxY) {
                    interferencies.add(p2);
                }
            }
        }
    }

    public int partTwo() {
        return 0;
    }
}
