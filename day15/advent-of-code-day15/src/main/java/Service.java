import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private final List<List<Character>> board = new ArrayList<>();
    private int maxX;
    private int maxY;
    private List<Character> directions = new ArrayList<>();
    private final Cord up = new Cord(0,-1);
    private final Cord right = new Cord(1,0);
    private final Cord down = new Cord(0,1);
    private final Cord left = new Cord(-1,0);

    public void readInput(Path path) {
        StringBuilder directionsString = new StringBuilder();
        boolean isBoard = true;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    if (!isBoard) {
                        directionsString.append(line.strip());
                    } else {
                        List<Character> row = new ArrayList<>();
                        for (char c : line.toCharArray()) {
                            row.add(c);
                        }
                        board.add(row);
                    }
                } else {
                    isBoard = false;
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
        for (char c: directionsString.toString().toCharArray()) {
            directions.add(c);
        }

        maxX = board.getFirst().size();
        maxY = board.size();
    }
    public long partOne() {
        Cord startPos = findRobot();
        for (char direction : directions) {
            startPos = moveRobot(startPos, direction);
        }
        return calcGPS();
    }

    private long calcGPS() {
        long total = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x= 0; x < maxX; x++) {
                if (board.get(y).get(x) == 'O') {
                    total += (y * 100) + x;
                }
            }
        }
        return total;
    }

    private void drawBoard() {
        for (List<Character> row: board) {
            System.out.println(row);
        }
        System.out.println("");
    }

    private Cord moveRobot(Cord startPos, char direction) {
        Cord defDirection = getDirection(direction);
        Cord nextStep = new Cord(startPos.getPosX() + defDirection.getPosX(), startPos.getPosY() + defDirection.getPosY());
        char nextValue = board.get(nextStep.getPosY()).get(nextStep.getPosX());
        if (nextValue == '.') {
            board.get(nextStep.getPosY()).set(nextStep.getPosX(),'@');
            board.get(startPos.getPosY()).set(startPos.getPosX(),'.');
            return nextStep;
        } else if (nextValue == '#') {
            return startPos;
        } else {
            List<Cord> boxes = new ArrayList<>();
            boxes.add(nextStep);
            while (!(nextValue == '.' || nextValue == '#')) {
                nextStep = new Cord(nextStep.getPosX() + defDirection.getPosX(), nextStep.getPosY() + defDirection.getPosY());
                nextValue = board.get(nextStep.getPosY()).get(nextStep.getPosX());
                if (nextValue == 'O') {
                    boxes.add(nextStep);
                } else if (nextValue == '.') {
                    board.get(nextStep.getPosY()).set(nextStep.getPosX(),'O');
                    for (int i = boxes.size() - 1; i > 0; i--) {
                        board.get(boxes.get(i).getPosY()).set(boxes.get(i).getPosX(),'O');
                    }
                    board.get(boxes.getFirst().getPosY()).set(boxes.getFirst().getPosX(),'@');
                    board.get(startPos.getPosY()).set(startPos.getPosX(),'.');
                    return boxes.getFirst();
                }
            }
            return startPos;
        }
    }

    private Cord getDirection(char direction) {
        Cord defDirection;
        switch (direction) {
            case '^':
                defDirection = up;
                break;
            case '>':
                defDirection = right;
                break;
            case 'v':
                defDirection = down;
                break;
            default:
                defDirection = left;
                break;
        }
        return defDirection;
    }

    private Cord findRobot() {
        for (int y = 0; y < maxY; y++) {
            for (int x= 0; x < maxX; x++) {
                if (board.get(y).get(x) == '@') {
                    return new Cord(x, y);
                }
            }
        }
        return new Cord(-1, -1);
    }

    public int partTwo() {
        return 0;
    }
}

