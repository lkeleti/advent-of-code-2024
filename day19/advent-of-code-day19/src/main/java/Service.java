import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final List<String> patterns = new ArrayList<>();
    private final List<String> towels = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            line = br.readLine();
            for (String data: line.split(",")) {
                patterns.add(data);
            }
            br.readLine();
            while ((line = br.readLine()) != null) {
                towels.add(line);
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
