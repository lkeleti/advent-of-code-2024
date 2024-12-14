import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    public final List<List<Character>> board = new ArrayList<>();
    private int maxX;
    private int maxY;
    private final Cord up = new Cord(0,-1);
    private final Cord down = new Cord(0,1);
    private final Cord left = new Cord(-1,0);
    private final Cord right = new Cord(1,0);
    private final List<Cord> directions = List.of(up, down, left, right);
    private final Cord leftUpCorner = new Cord(-0.5,-0.5);
    private final Cord rightUpCorner = new Cord(0.5, -0.5);
    private final Cord rightDownCorner = new Cord(0.5,0.5);
    private final Cord leftDownCorner = new Cord(-0.5,0.5);
    private final List<Cord> directionsCorner = List.of(leftUpCorner, rightUpCorner, rightDownCorner, leftDownCorner);

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
    }
    public long partOne() {
        List<List<Cord>> regions = dfs();
        return calcTotalPrice(regions);
    }

    private List<List<Cord>> dfs() {
        List<List<Cord>> regions = new ArrayList<>();
        Set<Cord> seen = new HashSet<>();
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                Cord startCord = new Cord(x,y);
                if (!seen.contains(startCord)) {
                    seen.add(startCord);
                    List<Cord> region = new ArrayList<>();
                    region.add(startCord);
                    Queue<Cord> q = new ArrayDeque<>();
                    q.add(startCord);
                    char startValue = board.get(x).get(y);
                    findNeighbor(q, startValue, region);
                    seen.addAll(region);
                    regions.add(region);
                }
            }
        }
        return regions;
    }

    private void findNeighbor(Queue<Cord> q, char startValue, List<Cord> region) {
        while (!q.isEmpty()) {
            Cord nextCord = q.poll();
            for (Cord nextStep: directions){
                Cord defCord = new Cord(nextCord.getPosX() + nextStep.getPosX(),
                        nextCord.getPosY() + nextStep.getPosY());
                if (!(defCord.getPosX() < 0 ||
                        defCord.getPosY() < 0 ||
                        defCord.getPosX() >= maxX ||
                        defCord.getPosY() >= maxY)){
                    char defValue = board.get((int)defCord.getPosX()).get((int)defCord.getPosY());
                    if (startValue == defValue && !region.contains(defCord)) {
                            region.add(defCord);
                            q.add(defCord);
                        }

                }
            }
        }
    }

    private int calcTotalPrice(List<List<Cord>> regions) {
        int total = 0;
        for (List<Cord> region: regions) {
            total += (region.size() * calcPerimeter(region));
        }
        return total;
    }

    private int calcPerimeter(List<Cord> region) {
        int corners = 0;
        for (Cord defCord: region) {
            corners += 4;
            for (Cord nextStep: directions) {
                Cord nextCord = new Cord(defCord.getPosX() + nextStep.getPosX(),
                defCord.getPosY() + nextStep.getPosY());
                if (region.contains(nextCord)) {
                    corners -=1;
                }
            }
        }
        return corners;
    }

    private int calcCorners(List<Cord> region) {
        Set<Cord> cornerCandidates = new HashSet<>();
        for (Cord startCord: region) {
            for (Cord nextStep: directionsCorner) {
                Cord nextCord = new Cord(startCord.getPosX() + nextStep.getPosX(), startCord.getPosY() + nextStep.getPosY());
                cornerCandidates.add(nextCord);
            }
        }

        int cornerCount = 0;
        for (Cord corner: cornerCandidates) {
            List<Boolean> config = new ArrayList<>();
            for (Cord nextStep: directionsCorner) {
                Cord nextCord = new Cord(corner.getPosX() + nextStep.getPosX(), corner.getPosY() + nextStep.getPosY());
                config.add(region.contains(nextCord));
            }
            int number = config.stream()
                    .mapToInt(value -> (value) ? 1 : 0)
                    .sum();
            if (number == 1) {
                cornerCount +=1;
            } else if (number == 2) {
                if ((config.get(0) && !config.get(1) && config.get(2) && !config.get(3)) ||
                        (!config.get(0) && config.get(1) && !config.get(2) && config.get(3))) {
                    cornerCount += 2;
                }
            } else if (number == 3) {
                cornerCount += 1;
            }
        }
        return cornerCount;
    }

    public int partTwo() {
        List<List<Cord>> regions = dfs();
        return calcTotalPriceByCorners(regions);
    }

    private int calcTotalPriceByCorners(List<List<Cord>> regions) {
        int total = 0;
        for (List<Cord> region: regions) {
            total += (region.size() * calcCorners(region));
        }
        return total;
    }
}
