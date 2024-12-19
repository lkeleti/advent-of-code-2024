import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


public class Service {
    private final int BOARD_SIZE = 71;
    private final List<List<Character>> board = new ArrayList<>();
    private final List<Cord> bytesList = new ArrayList<>();
    private final Cord startCord = new Cord(0,0);
    private final Cord endCord = new Cord(BOARD_SIZE-1,BOARD_SIZE-1);
    private final Cord up = new Cord(0,-1);
    private final Cord right = new Cord(1,0);
    private final Cord down = new Cord(0,1);
    private final Cord left = new Cord(-1,0);

    private final List<Cord> directions = List.of(up, right, down, left);

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(",");
                bytesList.add(new Cord(Integer.parseInt(datas[0]),Integer.parseInt(datas[1])));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }

    public long partOne() {
        clearMemory();
        fillMemory(1024);
        dumpMemory();
        List<Cord> seen = new ArrayList<>();
        Queue<Node> queue = new PriorityQueue<>();
        queue.add( new Node(0, startCord));

        while (!queue.isEmpty()) {
            Node defStep = queue.poll();
            seen.add(defStep.getPosition());

            for (Cord direction: directions) {
                int posX = defStep.getPosition().getPosX() + direction.getPosX();
                int posY = defStep.getPosition().getPosY() + direction.getPosY();
                Node nextStep = new Node(defStep.getStep() + 1, new Cord(posX, posY));

                if (!(posX < 0 || posY < 0 || posX > BOARD_SIZE || posY > BOARD_SIZE )) {
                    if (nextStep.getPosition().equals(endCord) ) {
                        return nextStep.getStep();
                    } else {
                        if (!seen.contains(nextStep.getPosition())) {
                            queue.add(nextStep);
                        }
                    }
                }
            }
        }
        return -1;
    }

    private void dumpMemory() {
        for (List<Character> row : board) {
            System.out.println(row.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining()));
        }
    }

    private void fillMemory(int counter) {
        for (int i = 0; i < counter; i++) {
            int x = bytesList.get(i).getPosX();
            int y = bytesList.get(i).getPosY();
            board.get(y).set(x, '#');
        }
    }

    private void clearMemory() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                row.add('.');
            }
            board.add(row);
        }
    }

    public int partTwo() {
        return -0;
    }
}
