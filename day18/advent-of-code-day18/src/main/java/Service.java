import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Service {
    private final int BOARD_SIZE = 71;
    private final List<List<Character>> board = new ArrayList<>();
    private final List<Cord> bytesList = new ArrayList<>();
    private final Cord startCord = new Cord(0,0);
    private final Cord endCord = new Cord(BOARD_SIZE-1,BOARD_SIZE-1);

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
        return 0;
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
