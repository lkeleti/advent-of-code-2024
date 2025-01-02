import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
        Map<List<Integer>, Integer> bananas = new HashMap<>();

        for (Long secretNumber: secretNumbers) {
            Set<List<Integer>> seen = new HashSet<>();
            List<Integer> price = new ArrayList<>();
            price.add((int) (secretNumber % 10));
            for (int i = 0; i < 2000; i++) {
                secretNumber = calculateSecretNumber(secretNumber);
                price.add((int) (secretNumber % 10));
                if (price.size() > 5) {
                    List<Integer> pattern = new ArrayList<>();
                    int a = price.get(i-4);
                    int b = price.get(i-3);
                    int c = price.get(i-2);
                    int d = price.get(i-1);
                    int e = price.get(i);

                    pattern.add(a-b);
                    pattern.add(b-c);
                    pattern.add(c-d);
                    pattern.add(d-e);

                    if (!seen.contains(pattern)){
                        seen.add(pattern);
                        if (bananas.containsKey(pattern)) {
                            bananas.put(pattern, bananas.get(pattern) + e);
                        } else {
                            bananas.put(pattern, e);
                        }
                    }
                }
            }
        }

        return bananas.values().stream().mapToInt(value -> value).max().getAsInt();
    }
}
