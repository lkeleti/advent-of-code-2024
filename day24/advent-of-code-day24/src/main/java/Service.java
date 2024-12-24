import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private final Map<String, Integer> knownValues = new HashMap<>();
    private final Map<String, Formula> formulas = new HashMap<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            boolean isValues = true;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    isValues = false;
                    line = br.readLine();
                }

                if (isValues) {
                    String[] datas = line.split(": ");
                    knownValues.put(datas[0], Integer.parseInt(datas[1]));
                } else {
                    line = line.replace("-> ", "");
                    String[] datas = line.split(" ");
                    formulas.put(datas[3], new Formula(datas[1], datas[0], datas[2], datas[3]));
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        List<String> zedsValues = new ArrayList<>();
        List<String> zeds = formulas.keySet().stream()
                .filter(s -> s.startsWith("z"))
                .sorted()
                .toList()
                .reversed();
        for (String z: zeds) {
            zedsValues.add(String.valueOf(calc(z)));
        }
        return Long.parseLong(String.join("", zedsValues),2);
    }

    private Integer calc(String wire) {
        if (knownValues.containsKey(wire)) {
            return knownValues.get(wire);
        }
        String operation = formulas.get(wire).getOperation();
        String input1 = formulas.get(wire).getInput1();
        String input2 = formulas.get(wire).getInput2();

        if (operation.equals("AND")) {
            knownValues.put(wire, calc(input1) & calc(input2));
        } else if(operation.equals("OR")) {
            knownValues.put(wire, calc(input1) | calc(input2));
        } else {
            knownValues.put(wire, calc(input1) ^ calc(input2));
        }
        return knownValues.get(wire);
    }

    public int partTwo() {
        return 0;
    }
}

