import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final List<Cord> DIRECTIONS = List.of(new Cord(-1,0), new Cord(0,1), new Cord(1,0), new Cord(0,-1));
    private final String[] DIRECTION_NAMES = {"up", "right", "down", "left"};
    private final List<List<Character>> maze = new ArrayList<>();
    private Cord start = null;
    private Cord end = null;
    private int leastCost;

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char c: line.toCharArray()) {
                    row.add(c);
                }
                maze.add(row);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }

        for (int i = 0; i < maze.size(); i++) {
            List<Character> row = maze.get(i);
            if (row.contains('S')) {
                start = new Cord(i, row.indexOf('S'));
            }
            if (row.contains('E')) {
                end = new Cord(i, row.indexOf('E'));
            }
        }
    }

    public long partOne() {
        leastCost = dijkstra(maze, start, end);
        return leastCost;
    }

    private int dijkstra(List<List<Character>> maze, Cord start, Cord end) {
        int rows = maze.size();
        int cols = maze.getFirst().size();

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, start.getPosX(), start.getPosY(), 1});

        Map<String, Integer> visited = new HashMap<>();

        int leastCost = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int cost = current[0];
            int x = current[1];
            int y = current[2];
            int dirIdx = current[3];

            if (x == end.getPosX() && y == end.getPosY()) {
                leastCost = Math.min(leastCost, cost);
                continue;
            }

            String key = x + "," + y + "," + dirIdx;
            if (visited.containsKey(key) && visited.get(key) <= cost) {
                continue;
            }

            visited.put(key, cost);

            int nx = x + DIRECTIONS.get(dirIdx).getPosX();
            int ny = y + DIRECTIONS.get(dirIdx).getPosY();
            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && maze.get(nx).get(ny) != '#') {
                pq.offer(new int[]{cost + 1, nx, ny, dirIdx});
            }

            int newDirIdx = (dirIdx - 1 + 4) % 4;
            pq.offer(new int[]{cost + 1000, x, y, newDirIdx});

            newDirIdx = (dirIdx + 1) % 4;
            pq.offer(new int[]{cost + 1000, x, y, newDirIdx});
        }

        return leastCost;
    }

    // BFS to find all least-cost paths and count unique tiles
    private int bfsAllLeastCostPaths(List<List<Character>> maze, Cord start, Cord end, int leastCost) {
        int rows = maze.size();
        int cols = maze.getFirst().size();

        // Sor: (x, y, irány, költség)
        Queue<HolderForQueue> queue = new LinkedList<>();
        List<Integer[]> startPath = new ArrayList<>();
        startPath.add(new Integer[]{start.getPosX(), start.getPosY()});
        queue.offer(new HolderForQueue(new int[]{start.getPosX(), start.getPosY(), 1, 0}, startPath));  // Kezdés kelet felé nézve (irány index 1), path

        // Halmaz az egyedi meglátogatott mezőkhöz (koordináta + irány)
        Set<String> uniqueTiles = new HashSet<>();

        // Látogatott szótár: kulcs = (x, y, irány), érték = költség
        Map<String, Integer> visited = new HashMap<>();

        while (!queue.isEmpty()) {
            HolderForQueue currentHolder = queue.poll();
            int[] current = currentHolder.getIntDatas();
            List<Integer[]> path = currentHolder.getPath();
            int x = current[0];
            int y = current[1];
            int dirIdx = current[2];
            int cost = current[3];

            // Ha a költség meghaladja a legkisebb költséget, kihagyjuk ezt az utat
            if (cost > leastCost) {
                continue;
            }

            // Ha elérjük a célt és a költség megegyezik a legkisebb költséggel, hozzáadjuk a mezőt az egyedi mezőkhöz
            if (x == end.getPosX() && y == end.getPosY() && cost == leastCost) {
                for (Integer[] p : path) {
                    uniqueTiles.add(p[0] + "," + p[1]);
                }
                continue;
            }

            // Ha ezt az állapotot már látogattuk kisebb költséggel, kihagyjuk
            String key = x + "," + y + "," + dirIdx;
            if (visited.containsKey(key) && visited.get(key) < cost) {
                continue;
            }

            // Megjelöljük az állapotot, mint látogatott
            visited.put(key, cost);
            //uniqueTiles.add(key);

            // Próbálunk előre lépni
            int nx = x + DIRECTIONS.get(dirIdx).getPosX();
            int ny = y + DIRECTIONS.get(dirIdx).getPosY();
            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && maze.get(nx).get(ny) != '#') {
                List<Integer[]> newPath = currentHolder.copyPath();
                newPath.add(new Integer[]{nx, ny});
                queue.offer(new HolderForQueue(new int[]{nx, ny, dirIdx, cost + 1}, newPath));
            }

            // Próbálunk balra fordulni (90 fok)
            int newDirIdx = (dirIdx - 1 + 4) % 4;
            queue.offer(new HolderForQueue(new int[]{x, y, newDirIdx, cost + 1000}, currentHolder.copyPath()));

            // Próbálunk jobbra fordulni (90 fok)
            newDirIdx = (dirIdx + 1) % 4;
            queue.offer(new HolderForQueue(new int[]{x, y, newDirIdx, cost + 1000}, currentHolder.copyPath()));
        }

        return uniqueTiles.size();
    }

    public int partTwo() {
        int uniqueTilesCount = bfsAllLeastCostPaths(maze, start, end, leastCost);
        return uniqueTilesCount;
    }
}
