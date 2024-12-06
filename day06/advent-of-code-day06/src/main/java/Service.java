import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public final List<List<Character>> board = new ArrayList<>();
    public final Cord startCord = new Cord(-1,-1);
    public final Cord maxCord = new Cord(-1,-1);

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
        soldPOne(board);
        return countX(board);
    }

    private boolean soldPOne(List<List<Character>> board) {
        List<CordDir> visited = new ArrayList<>();
        int defX = startCord.getPosX();
        int defY = startCord.getPosY();
        Directions defDirection = Directions.UP;
        while (!(defX < 0 || defX >= maxCord.getPosX() || defY < 0 || defY >= maxCord.getPosY())) {
            board.get(defY).set(defX, 'X');
            visited.add(new CordDir(new Cord(defX,defY),defDirection));

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
            CordDir nextCordDir = new CordDir(new Cord(nextX, nextY), defDirection);
            if (visited.contains(nextCordDir)) {
                return true;
            }
            defX = nextX;
            defY = nextY;
        }
        return false;
    }

    private int countX(List<List<Character>> board) {
        int counter = 0;
        String row = "";
        for (int i = 0; i < maxCord.getPosX(); i++) {
            for (int j = 0; j < maxCord.getPosY(); j++) {
                if (board.get(j).get(i) == 'X') {
                    counter++;
                }
                row += board.get(i).get(j);
            }
            System.out.println(row);
            row = "";
        }
        return counter;
    }

    public int partTwo() {
        int counter = 0;
        for (int i = 0; i < maxCord.getPosX(); i++) {
            for (int j = 0; j < maxCord.getPosY(); j++) {
                if (board.get(j).get(i) != '#' && !(j == startCord.getPosY() && i == startCord.getPosX())) {
                    board.get(j).set(i, '#');
                    if (soldPOne(board)) {
                        counter++;
                    }
                    board.get(j).set(i, '.');
                }
            }
        }
        return counter - 1;
        //7454 high
    }
}
