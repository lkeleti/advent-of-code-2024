import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<List<Integer>> prices = getPrices();
        List<List<Integer>> differences = getDifferences(prices);
        List<Integer> patterns = differences.getFirst();

        int maxBananas = -1;
        for(int i = 0; i < patterns.size()-4; i+=4){
            List<Integer> pattern = new ArrayList<>();
            Integer bananas = 0;
            pattern.add(patterns.get(i));
            pattern.add(patterns.get(i+1));
            pattern.add(patterns.get(i+2));
            pattern.add(patterns.get(i+3));

            bananas += prices.getFirst().get(i + 4);
            for (int j = 1; j < differences.size(); j++) {
                for (int k = 0; k < differences.get(j).size() - 5; k++) {
                    if ((differences.get(j).get(k) == pattern.get(0)) &&
                            (differences.get(j).get(k + 1) == pattern.get(1)) &&
                            (differences.get(j).get(k + 2) == pattern.get(2)) &&
                            (differences.get(j).get(k + 3) == pattern.get(3))) {
                        bananas += prices.get(j).get(k + 4);
                        break;
                    }
                }
            }
            if (bananas > maxBananas) {
                maxBananas = bananas;
            }
        }
        return maxBananas;
    }

    private List<List<Integer>> getDifferences(List<List<Integer>> prices) {
        List<List<Integer>> differences = new ArrayList<>();
        for (List<Integer> price : prices) {
            List<Integer> dif = new ArrayList<>();
            for (int i= 0; i < price.size()-1; i++) {
                dif.add(price.get(i+1) - price.get(i));
            }
            differences.add(dif);
        }
        return differences;
        //1793 low
    }


    private List<List<Integer>> getPrices() {
        List<List<Integer>> prices = new ArrayList<>();
        for (Long secretNumber: secretNumbers) {
            List<Integer> price = new ArrayList<>();
            price.add((int) (secretNumber % 10));
            for (int i = 0; i < 2000; i++) {
                secretNumber = calculateSecretNumber(secretNumber);
                price.add((int) (secretNumber % 10));
            }
            prices.add(price);
        }
        return prices;
    }
}
