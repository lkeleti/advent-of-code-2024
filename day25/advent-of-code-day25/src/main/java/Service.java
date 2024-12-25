import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Service {
    private final List<List<Integer>> keys = new ArrayList<>();
    private final List<List<Integer>> locks = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> def = new ArrayList<>(
                    Arrays.asList(-1,-1,-1,-1,-1));
                char isLock = '#';
                if (line.equals("#####")) {
                    isLock = '.';
                }

                for (int i = 0; i < 6; i++) {
                    line = br.readLine();
                    char[] chars = line.toCharArray();
                    for (int j = 0; j < chars.length; j++) {
                        if (chars[j] == isLock) {
                            def.set(j, def.get(j) + 1);
                        }
                    }
                }
                if (isLock == '.') {
                    for (int i = 0; i < def.size(); i++) {
                        def.set(i, 5 - def.get(i));
                    }
                    locks.add(def);
                } else {
                    keys.add(def);
                }
                br.readLine();
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        int counter = 0;
        for (List<Integer> key : keys) {
            for (List<Integer> lock : locks) {
                boolean valid = true;
                for(int i = 0; i < 5; i++) {
                    if (key.get(i) + lock.get(i) > 5) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    counter++;
                }
            }
        }
        return counter;
    }
    public int partTwo() {
        return 0;
    }
}

