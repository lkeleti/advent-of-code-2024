import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Service {
    public final Map<Integer, List<Integer>> rulesBefore = new TreeMap<>();
    public final Map<Integer, List<Integer>> rulesAfter = new TreeMap<>();

    public final List<List<Integer>> pages = new ArrayList<>();

    public void readInput(Path path) {
        boolean isPages = false;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    isPages = true;
                    continue;
                }
                if (!isPages) {
                    String[] datas = line.split("\\|");
                    int before = Integer.parseInt(datas[0]);
                    int after = Integer.parseInt(datas[1]);

                    if (rulesBefore.containsKey(before)) {
                        rulesBefore.get(before).add(after);
                    } else {
                        rulesBefore.put(before,new ArrayList<>(after));
                    }

                    if (rulesAfter.containsKey(after)) {
                        rulesAfter.get(after).add(before);
                    } else {
                        rulesAfter.put(after,new ArrayList<>(before));
                    }
                } else {
                    String[] datas = line.split(",");
                    List<Integer> numbers = new ArrayList<>();
                    for (String data:datas){
                        numbers.add(Integer.parseInt(data));
                    }
                    pages.add(numbers);
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        return 0;
    }
    public int partTwo() {
        return 0;
    }
}
