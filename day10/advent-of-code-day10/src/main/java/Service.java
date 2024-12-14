import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private final List<List<Integer>> board = new ArrayList<>();
    private int maxX = -1;
    private int maxY = -1;
    private final Cord right = new Cord(1,0);
    private final Cord down = new Cord(0,1);
    private final Cord left = new Cord(-1,0);
    private final Cord up = new Cord(0,-1);
    private final List<Cord> directions = List.of(right, down, left, up);

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
        List<Cord> starts = new ArrayList<>();
        findStarts(starts);
        int count = 0;
        for (Cord start: starts) {
            count += dfs(start);
        }
        return count;
    }

    private void findStarts(List<Cord> starts) {
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (board.get(i).get(j) == 0) {
                    starts.add(new Cord(i,j));
                }
            }
        }
    }

    private int dfs(Cord start) {
        Set<Cord> seen = new HashSet<>();
        Queue<Cord> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Cord defCord = queue.poll();
            int defValue = board.get(defCord.getPosX()).get(defCord.getPosY());
            if (defValue == 9) {
                seen.add(defCord);
            }
            for (Cord nextStep : directions) {
                Cord nextCord = new Cord(defCord.getPosX() + nextStep.getPosX(), defCord.getPosY() + nextStep.getPosY());
                if (!(nextCord.getPosX() < 0 || nextCord.getPosY() < 0 || nextCord.getPosX() >= maxX || nextCord.getPosY() >= maxY)) {
                    int nextValue = board.get(nextCord.getPosX()).get(nextCord.getPosY());
                    if (defValue + 1 == nextValue) {
                        queue.add(nextCord);
                    }
                }
            }
        }

        return seen.size();
    }

    public int partTwo() {
        List<Cord> starts = new ArrayList<>();
        findStarts(starts);
        int count = 0;
        for (Cord start: starts) {
            count += dfs2(start);
        }
        return count;
    }

    private int dfs2(Cord start) {
        Map<Cord, Integer> seen = new HashMap<>();
        Queue<Cord> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Cord defCord = queue.poll();
            int defValue = board.get(defCord.getPosX()).get(defCord.getPosY());
            if (defValue == 9) {
                if (!seen.containsKey(defCord)) {
                    seen.put(defCord, 1);
                } else {
                    seen.put(defCord, seen.get(defCord) + 1);
                }
            }
            for (Cord nextStep : directions) {
                Cord nextCord = new Cord(defCord.getPosX() + nextStep.getPosX(), defCord.getPosY() + nextStep.getPosY());
                if (!(nextCord.getPosX() < 0 || nextCord.getPosY() < 0 || nextCord.getPosX() >= maxX || nextCord.getPosY() >= maxY)) {
                    int nextValue = board.get(nextCord.getPosX()).get(nextCord.getPosY());
                    if (defValue + 1 == nextValue) {
                        queue.add(nextCord);
                    }
                }
            }
        }

        return seen.values().stream()
                .mapToInt(value -> value)
                .sum();
    }
}
