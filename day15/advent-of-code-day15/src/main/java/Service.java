import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private final List<List<Character>> board = new ArrayList<>();
    private final List<List<Character>> board2 = new ArrayList<>();
    private int maxX;
    private int maxY;
    private int max2X;
    private int max2Y;
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
        createBoard2();
        Cord startPos = findRobot(1);
        for (char direction : directions) {
            startPos = moveRobot(startPos, direction);
        }
        return calcGPS();
    }

    private void createBoard2() {
        for (int y = 0; y < maxY; y++) {
            List<Character> row = new ArrayList<>();
            for (int x= 0; x < maxX; x++) {
                char defValue = board.get(y).get(x);
                if ( defValue == '#') {
                    row.add('#');
                    row.add('#');
                } else if (defValue == 'O') {
                    row.add('[');
                    row.add(']');
                } else if (defValue =='.') {
                    row.add('.');
                    row.add('.');
                } else if (defValue == '@') {
                    row.add('@');
                    row.add('.');
                }
            }
            board2.add(row);
        }
        max2X = board2.getFirst().size();
        max2Y = board2.size();
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
    private void drawBoard2() {
        for (List<Character> row: board2) {
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

    private Cord findRobot(int part) {
        int maxXValue;
        int maxYValue;

        if (part == 1) {
            maxXValue = maxX;
            maxYValue = maxY;
        } else {
            maxXValue = max2X;
            maxYValue = max2Y;
        }
        for (int y = 0; y < maxYValue; y++) {
            for (int x= 0; x < maxXValue; x++) {
                char defValue;
                if (part == 1) {
                    defValue = board.get(y).get(x);
                } else {
                    defValue = board2.get(y).get(x);
                }
                if ( defValue== '@') {
                    return new Cord(x, y);
                }
            }
        }
        return new Cord(-1, -1);
    }

    public long partTwo() {
        Cord startPos = findRobot(2);
        drawBoard2();
        for (char direction : directions) {
            startPos = moveRobot2(startPos, direction);
            drawBoard2();
        }
        return calcGPS();
    }

    private Cord moveRobot2(Cord startPos, char direction) {
        Cord defDirection = getDirection(direction);
        Cord nextStep = new Cord(startPos.getPosX() + defDirection.getPosX(), startPos.getPosY() + defDirection.getPosY());
        char nextValue = board2.get(nextStep.getPosY()).get(nextStep.getPosX());
        if (nextValue == '.') {
            board2.get(nextStep.getPosY()).set(nextStep.getPosX(),'@');
            board2.get(startPos.getPosY()).set(startPos.getPosX(),'.');
            return nextStep;
        } else if (nextValue == '#') {
            return startPos;
        } else {
            List<Cord> boxes = new ArrayList<>();
            boxes.add(nextStep);
            while (!(nextValue == '.' || nextValue == '#')) {
                nextStep = new Cord(nextStep.getPosX() + defDirection.getPosX(), nextStep.getPosY() + defDirection.getPosY());
                nextValue = board2.get(nextStep.getPosY()).get(nextStep.getPosX());
                if (nextValue == '[' || nextValue == ']') {
                    boxes.add(nextStep);
                } else if (nextValue == '.') {
                    board2.get(nextStep.getPosY()).set(nextStep.getPosX(),']');
                    for (int i = boxes.size() - 1; i > 0; i--) {
                        if (i % 2 == 0) {
                            board2.get(boxes.get(i).getPosY()).set(boxes.get(i).getPosX(), '[');
                        } else {
                            board2.get(boxes.get(i).getPosY()).set(boxes.get(i).getPosX(), ']');
                        }
                    }
                    board2.get(boxes.getFirst().getPosY()).set(boxes.getFirst().getPosX(),'@');
                    board2.get(startPos.getPosY()).set(startPos.getPosX(),'.');
                    return boxes.getFirst();
                }
            }
            return startPos;
        }
    }
}

