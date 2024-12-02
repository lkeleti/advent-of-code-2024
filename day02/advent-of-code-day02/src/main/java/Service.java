import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public final List<List<Integer>> reports = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(" ");
                List<Integer> numbers = new ArrayList<>();
                for (String data : datas) {
                    numbers.add(Integer.parseInt(data));
                }
                reports.add(numbers);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        int counter = 0;
        for (List<Integer> numbers : reports) {
            if (isSafe(numbers)) {
                counter++;
            }
        }
        return counter;
    }

    private boolean isSafe(List<Integer> numbers) {
        LevelType defType = checkLevelType(numbers.get(0), numbers.get(1));
        boolean safe = true;
        for (int i = 1; i < numbers.size(); i++) {
            if (!(checkLevelType(numbers.get(i-1), numbers.get(i)) == defType &&
                    checkDistance(numbers.get(i-1), numbers.get(i)))) {
                safe = false;
            }
        }
        return safe;
    }

    private boolean checkDistance(Integer num1, Integer num2) {
        if (Math.abs(num1 - num2) > 0 && Math.abs(num1 - num2) < 4) {
            return true;
        }
        return false;
    }

    private LevelType checkLevelType(int num1, int num2) {
        return  num1 > num2? LevelType.INCREASING:LevelType.DECREASING;
    }

    public int partTwo() {
        int counter = 0;
        for (List<Integer> numbers : reports) {
            if (isSafe(numbers)) {
                counter++;
            } else {
                for (int i = 0; i < numbers.size(); i++) {
                    List<Integer> reducedList = new ArrayList<>(numbers);
                    reducedList.remove(i);
                    if (isSafe(reducedList)) {
                        counter++;
                        break;
                    }
                }
            }
        }
        return counter;
    }
}
