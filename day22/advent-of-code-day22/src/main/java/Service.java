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
        List<List<Integer>> prices = getPrices();
        Map<List<Integer>, Integer> bananas = new HashMap<>();

        for (Long secretNumber: secretNumbers) {
            Set<List<Integer>> seen = new HashSet<>();
            List<Integer> price = new ArrayList<>();
            price.add((int) (secretNumber % 10));
            for (int i = 0; i < 2000; i++) {
                secretNumber = calculateSecretNumber(secretNumber);
                price.add((int) (secretNumber % 10));
                if (price.size() > 6) {
                    List<Integer> pattern = new ArrayList<>();
                    int a = price.get(i-5);
                    int b = price.get(i-4);
                    int c = price.get(i-3);
                    int d = price.get(i-2);
                    int e = price.get(i-1);
                    int f = price.get(i);

                    pattern.add(a-b);
                    pattern.add(b-c);
                    pattern.add(c-d);
                    pattern.add(d-e);

                    if (!seen.contains(pattern)){
                        seen.add(pattern);
                        if (bananas.containsKey(pattern)) {
                            bananas.put(pattern, bananas.get(pattern) + f);
                        } else {
                            bananas.put(pattern, f);
                        }
                    }
                }
            }
        }

        return bananas.values().stream().mapToInt(value -> value).max().getAsInt();
    }

    public int partTwo1() {
        List<List<Integer>> prices = getPrices();
        List<List<Integer>> differences = getDifferences(prices);
        List<Integer> patterns = differences.getFirst();
        Set<String> seen = new HashSet<>();

        int maxBananas = -1;
        for(int i = 0; i < patterns.size()-3; i+=4){
            List<Integer> pattern = new ArrayList<>();
            Integer bananas = 0;
            pattern.add(patterns.get(i));
            pattern.add(patterns.get(i+1));
            pattern.add(patterns.get(i+2));
            pattern.add(patterns.get(i+3));

            if (!seen.contains(pattern.toString().replace("[","").replace("]","").replace(" ",""))) {
                seen.add(pattern.toString().replace("[","").replace("]","").replace(" ",""));

                bananas += prices.getFirst().get(i + 4);
                for (int j = 1; j < differences.size(); j++) {
                    for (int k = 0; k < differences.get(j).size() - 4; k++) {
                        if ((differences.get(j).get(k) == pattern.get(0)) &&
                                (differences.get(j).get(k + 1) == pattern.get(1)) &&
                                (differences.get(j).get(k + 2) == pattern.get(2)) &&
                                (differences.get(j).get(k + 3) == pattern.get(3))) {
                            bananas += prices.get(j).get(k + 4);
                            break;
                        }
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
        //1800 low
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
