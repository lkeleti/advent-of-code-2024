import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    List<Long> secretNumbers = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                secretNumbers.add(Long.parseLong(line));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        Long result = 0L;
        for (Long secretNumber: secretNumbers) {
            Long number = 2024L;
            for (int i = 0; i < 2000; i++) {
                secretNumber = calculateSecretNumber(secretNumber);
            }
            result += secretNumber;
        }
        return result;
    }

    private Long calculateSecretNumber(Long secretNumber) {
        secretNumber = mixSecretNumber(secretNumber, secretNumber * 64);
        secretNumber = secretNumberPrune(secretNumber);
        secretNumber = mixSecretNumber(secretNumber, secretNumber / 32);
        secretNumber = secretNumberPrune(secretNumber);
        secretNumber = mixSecretNumber(secretNumber, secretNumber * 2048);
        secretNumber = secretNumberPrune(secretNumber);
        return secretNumber;
    }

    private Long secretNumberPrune(Long secretNumber) {
        return secretNumber % 16777216;
    }

    private Long mixSecretNumber(Long secretNumber, Long result) {
        return result ^ secretNumber;
    }

    public int partTwo() {
        return 0;
    }
}
