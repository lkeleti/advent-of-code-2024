import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    public List<Long> stones = new ArrayList<>();
    public Map<Long,Long> numbers = new HashMap<>();

    public void readInput(Path path) {

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(" ");
                for (String data: datas) {
                    stones.add(Long.parseLong(data));
                    numbers.put(Long.parseLong(data),1L);
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        for (int i = 0; i < 25; i++) {
            calcChanges();
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
        for (int i = 0; i < 75; i++) {
            calcChanges2();
        }

        long total = 0l;
        for (long key: numbers.keySet()) {
            total += numbers.get(key);
        }
        return total;
    }

    private void calcChanges2() {
        Map<Long, Long> newNumbers = new HashMap<>();
        for (long key: numbers.keySet()) {
            long size = numbers.get(key);
            String stoneString = String.valueOf(key);
            if (key == 0) {
                if (!newNumbers.containsKey(1L)) {
                    newNumbers.put(1L, size);
                } else {
                    newNumbers.replace(1L, newNumbers.get(1L) + size);
                }
            } else if (stoneString.length() % 2 == 0) {
                int middle = stoneString.length() /2;
                long key1 = Long.parseLong(stoneString.substring(0,middle));
                long key2 = Long.parseLong(stoneString.substring(middle));
                if (!newNumbers.containsKey(key1)) {
                    newNumbers.put(key1, size);
                } else {
                    newNumbers.replace(key1, newNumbers.get(key1) + size);
                }
                if (!newNumbers.containsKey(key2)) {
                    newNumbers.put(key2, size);
                } else {
                    newNumbers.replace(key2, newNumbers.get(key2) + size);
                }
            } else {
                if (!newNumbers.containsKey(key*2024)) {
                    newNumbers.put(key*2024, size);
                } else {
                    newNumbers.replace(key*2024, newNumbers.get(key*2024) + size);
                }
            }
        }
        numbers = newNumbers;
    }
}
