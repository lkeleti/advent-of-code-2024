import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public List<Long> stones = new ArrayList<>();

    public void readInput(Path path) {

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(" ");
                for (String data: datas) {
                    stones.add(Long.parseLong(data));
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        for (int i = 0; i < 25; i++) {
            calcChanges();
            System.out.println(stones.size());
        }
        return stones.size();
    }

    private void calcChanges() {
        List<Long> newStones = new ArrayList<>();
        for (long stone: stones) {
            String stoneString = String.valueOf(stone);
            if (stone == 0) {
                newStones.add(1L);
            } else if (stoneString.length() % 2 == 0) {
                int middle = stoneString.length() /2;
                newStones.add(Long.parseLong(stoneString.substring(0,middle)));
                newStones.add(Long.parseLong(stoneString.substring(middle)));
            } else {
                newStones.add(stone*2024);
            }
        }
        stones = newStones;
    }

    public long partTwo() {
        for (int i = 0; i < 50; i++) {
            calcChanges();
            System.out.println(stones.size());
        }
        return stones.size();
    }
}
