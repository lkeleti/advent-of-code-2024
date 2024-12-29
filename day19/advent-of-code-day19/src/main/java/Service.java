import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private final List<String> patterns = new ArrayList<>();
    private int maxLen = 0;
    private final List<String> towels = new ArrayList<>();
    private final Map<String, Boolean> cache = new HashMap<>();
    private final Map<String, Long> cache2 = new HashMap<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            line = br.readLine();
            for (String data: line.split(", ")) {
                patterns.add(data);
            }
            br.readLine();
            while ((line = br.readLine()) != null) {
                towels.add(line);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }

        maxLen = patterns.stream()
                .mapToInt(String::length)
                .max().getAsInt();
    }

    private boolean findPattern(String word) {
        if (word.isEmpty()) {
            return true;
        }

        if (cache.containsKey(word)) {
            return cache.get(word);
        }

        for (int i = 0; i < Math.min(word.length(), maxLen) + 1; i++) {
            if (patterns.contains(word.substring(0,i)) && findPattern(word.substring(i))) {
                cache.put(word,true);
                return true;
            }
        }
        cache.put(word,false);
        return false;
    }

    public long partOne() {
        int counter = 0;
        for (String word: towels) {
            if (findPattern(word)) {
                counter++;
            }
        }
        return counter;
    }
    public Long partTwo() {
        long counter = 0;
        for (String word: towels) {
            counter += findPossabilities(word);
        }
        return counter;
    }

    private long findPossabilities(String word) {
        if (word.isEmpty()) {
            return 1;
        }
        long counter = 0;

        if (cache2.containsKey(word)) {
            return cache2.get(word);
        }

        for (int i = 0; i < Math.min(word.length(), maxLen) + 1; i++) {
            if (patterns.contains(word.substring(0,i))) {
                cache2.put(word,counter);
                counter += findPossabilities(word.substring(i));
            }
        }
        cache2.put(word,counter);
        return counter;

    }
}
