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
                findInterference(antennas.get(c),1);
            }
        }
        return interferencies.size();
    }

    private void findInterference(List<Cord> cords, int part) {
        for (int i = 0; i < cords.size()-1; i++) {
            for (int j = i + 1; j < cords.size(); j++) {
                int x1 = cords.get(i).getPosX();
                int y1 = cords.get(i).getPosY();
                int x2 = cords.get(j).getPosX();
                int y2 = cords.get(j).getPosY();

                if (part == 2) {
                    interferencies.add(cords.get(i));
                    interferencies.add(cords.get(j));
                }

                findFromP1(part, x1, x2, y1, y2);
                findFromP2(part, x2, x1, y2, y1);
            }
        }
    }

    private void findFromP2(int part, int x2, int x1, int y2, int y1) {
        boolean inside = true;
        int multiplier = 2;
        while (inside) {
            Cord p2 = new Cord(x2 + multiplier*(x1 - x2), y2 + multiplier*(y1 - y2));
            if (p2.getPosX() >= 0 && p2.getPosX() < maxX && p2.getPosY() >= 0 && p2.getPosY() < maxY) {
                interferencies.add(p2);
                if (part == 1) {
                    break;
                }
            } else {
                inside = false;
            }
            multiplier++;
        }
    }

    private void findFromP1(int part, int x1, int x2, int y1, int y2) {
        boolean inside = true;
        int multiplier = 2;
        while (inside) {
            Cord p1 = new Cord(x1 + multiplier*(x2 - x1), y1 + multiplier*(y2 - y1));
            if (p1.getPosX() >= 0 && p1.getPosX() < maxX && p1.getPosY() >= 0 && p1.getPosY() < maxY) {
                interferencies.add(p1);
                if (part == 1) {
                    break;
                }
            } else {
                inside = false;
            }
            multiplier++;
        }
    }

    public int partTwo() {
        for (char c : antennas.keySet()) {
            if (antennas.get(c).size() > 1) {
                findInterference(antennas.get(c),2);
            }
        }
        //drawBoard();
        return interferencies.size();
    }

    private void drawBoard() {
        for (Cord c: interferencies) {
            if (board.get(c.getPosX()).get(c.getPosY()) == '.') {
                board.get(c.getPosX()).set(c.getPosY(),'#');
            }
        }

        for (List<Character> row: board) {
            String s = "";
            for (char c: row) {
                s += c;
            }
            System.out.println(s);
        }
    }
}
