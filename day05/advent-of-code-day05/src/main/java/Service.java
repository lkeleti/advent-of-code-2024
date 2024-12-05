import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
                        rulesBefore.put(before,new ArrayList<>());
                        rulesBefore.get(before).add(after);
                    }

                    if (rulesAfter.containsKey(after)) {
                        rulesAfter.get(after).add(before);
                    } else {
                        rulesAfter.put(after,new ArrayList<>(before));
                        rulesAfter.get(after).add(before);
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
        int total = 0;
        for (List<Integer> page: pages) {
            total += getMiddle(page);
        }
        return total;
    }

    private int getMiddle(List<Integer> page) {
        for (int i = 0; i < page.size() - 1; i++) {
            for (int j = i+1; j < page.size(); j++) {
                int key = page.get(i);
                int nextNumber = page.get(j);
                if (rulesBefore.containsKey(key)) {
                    if (!rulesBefore.get(key).contains(nextNumber)) {
                        return 0;
                    }
                } else return 0;
            }
        }
        return page.get(page.size()/2);
    }
    private int correctOrder(List<Integer> page) {
        int res = page.get(page.size()/2);
        for (int i = 0; i < page.size() - 1; i++) {
            for (int j = i+1; j < page.size(); j++) {
                int key = page.get(i);
                int nextNumber = page.get(j);
                if (rulesBefore.containsKey(key)) {
                    if (!rulesBefore.get(key).contains(nextNumber)) {
                        Collections.swap(page,i,j);
                        res = 0;
                    }
                } else {
                    Collections.swap(page,i,j);
                    res = 0;
                }
            }
        }
        return res;
    }
    public long partTwo() {
        ListIterator<List<Integer>> iter = pages.listIterator();
        while(iter.hasNext()){
            if (correctOrder(iter.next()) != 0) {
                iter.remove();
            }
        }
        return partOne();
    }
}
