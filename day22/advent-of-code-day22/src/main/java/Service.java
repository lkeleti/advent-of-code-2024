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
        List<List<Integer>> prices = getPrices();
        List<String> differences = getDifferences(prices);
        String[] patterns = differences.getFirst().split(",");

        int maxBananas = -1;
        for(int i = 0; i < patterns.length-4; i+=4){
            String pattern = "";
            Integer bananas = 0;
            pattern += patterns[i];
            pattern += patterns[i+1];
            pattern += patterns[i+2];
            pattern += patterns[i+3];

            if (pattern.startsWith("-21")) {
                System.out.println("pattern");
            }

            bananas += prices.getFirst().get(i + 4);
            for (int j = 1; j < differences.size(); j++) {
                int position = differences.get(j).indexOf(pattern);
                if (position != -1) {
                    bananas += prices.get(j).get(position  + 4);
                }
            }
            if (bananas > maxBananas) {
                maxBananas = bananas;
            }
        }
        return maxBananas;
    }

    private List<String> getDifferences(List<List<Integer>> prices) {
        List<String> differences = new ArrayList<>();
        for (List<Integer> price : prices) {
            StringBuilder dif = new StringBuilder();
            for (int i= 0; i < price.size()-1; i++) {
                dif.append(price.get(i+1) - price.get(i));
                dif.append(",");
            }
            differences.add(dif.toString());
        }
        return differences;
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
