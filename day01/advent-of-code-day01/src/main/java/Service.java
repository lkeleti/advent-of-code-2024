import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    public final List<Integer> left = new ArrayList<>();
    public final List<Integer> right = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(" {3}");
                left.add(Integer.parseInt(datas[0]));
                right.add(Integer.parseInt(datas[1]));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        List<Integer> leftSorted = left.stream().sorted().toList();
        List<Integer> rightSorted = right.stream().sorted().toList();
        int summa = 0;
        for (int i =0; i< leftSorted.size(); i++) {
            summa += Math.abs(rightSorted.get(i) - leftSorted.get(i));
        }
        return summa;
    }
    public int partTwo() {
        Map<Integer, Integer> rightFreq = new TreeMap<>();
        for (int defRight: right) {
            if (rightFreq.containsKey(defRight)) {
                rightFreq.put(defRight, rightFreq.get(defRight) + 1 );
            } else{
                rightFreq.put(defRight, 1);
            }
        }
        return getSimilarityScore(rightFreq);
    }

    private int getSimilarityScore(Map<Integer, Integer> rightFreq) {
        int summa = 0;
        for (int defLeft : left) {
            if (rightFreq.containsKey(defLeft)) {
                summa += (rightFreq.get(defLeft) * defLeft);
            }
        }
        return summa;
    }
}
