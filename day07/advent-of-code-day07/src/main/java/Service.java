import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public final List<Equation> equations = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(":");
                String[] numbers = datas[1].substring(1) .split(" ");
                Equation equation = new Equation(Long.parseLong(datas[0]));
                for (String i: numbers) {
                    equation.addNumber(Integer.parseInt(i));
                }
                equations.add(equation);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        for (Equation e: equations) {
            checkValid(e,1);
        }
        return equations.stream()
                .filter(Equation::isValid)
                .mapToLong(Equation::getTestValue)
                .sum();
    }

    private void checkValid(Equation e, int part) {
        Long testValue = e.getTestValue();
        List<String> operators = generateOperators(e.getNumbers().size()-1, part);
        for (String operator: operators) {
            Long value = Long.valueOf(e.getNumbers().getFirst());
            for (int i = 0; i < operator.length(); i++) {
                if (operator.charAt(i) == '0') {
                    value += Long.valueOf(e.getNumbers().get(i + 1));
                } else if (operator.charAt(i) == '1') {
                    value *= Long.valueOf(e.getNumbers().get(i + 1));
                } else {
                    value = Long.parseLong(value.toString() + e.getNumbers().get(i + 1).toString());
                }
            }
            if (testValue.equals(value)) {
                e.setValid(true);
                break;
            }
        }
    }

    private List<String> generateOperators(int length,int part) {
        List<String> result = new ArrayList<>();
        generateBinaryStringsRecursive("", length, result, part);
        return result;
    }

    private void generateBinaryStringsRecursive(String current, int remainingLength, List<String> result, int part) {
        if (remainingLength == 0) {
            result.add(current);
            return;
        }
        generateBinaryStringsRecursive(current + "0", remainingLength - 1, result, part);
        generateBinaryStringsRecursive(current + "1", remainingLength - 1, result, part);
        if (part == 2) {
            generateBinaryStringsRecursive(current + "2", remainingLength - 1, result, part);
        }
    }

    public long partTwo() {
        for (Equation e: equations) {
            if (!e.isValid()) {
                    checkValid(e,2);
            }
        }
        return equations.stream()
                .filter(Equation::isValid)
                .mapToLong(Equation::getTestValue)
                .sum();
    }
}
