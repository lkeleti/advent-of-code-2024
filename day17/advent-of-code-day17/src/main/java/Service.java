import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private int registerA;
    private int registerB;
    private int registerC;

    private final List<Integer> program = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                registerA = Integer.parseInt(line.replace("Register A: ", ""));
                line = br.readLine();
                registerB = Integer.parseInt(line.replace("Register B: ", ""));
                line = br.readLine();
                registerC = Integer.parseInt(line.replace("Register C: ", ""));
                line = br.readLine();
                line = br.readLine().replace("Program: ","");
                String[] datas = line.split(",");
                for (String data: datas) {
                    program.add(Integer.parseInt(data));
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public String partOne() {
        List<Integer> output = new ArrayList<>();
        for (int ip = 0; ip < program.size(); ip+=2) {
            int opcode =  program.get(ip);
            int operand = program.get(ip + 1);
            operand = getOperandValue(operand);
            switch (opcode) {
                case 0 : adv(operand);
                break;
                case 1 : bxl(operand);
                    break;
                case 2 : bst(operand);
                    break;
                case 3 : ip = jnz(operand, ip);
                    break;
                case 4 : bxc();
                    break;
                case 5 : out(operand, output);
                    break;
                case 6 : bdv(operand);
                    break;
                case 7 : cdv(operand);
                    break;
            }
        }
        return String.join(",", output.stream().map(String::valueOf).toList());
    }

    private void cdv(int operand) {
        int result =  (int)(registerA / Math.pow(2,operand));
        registerC = result;
    }

    private void bdv(int operand) {
        int result =  (int)(registerA / Math.pow(2,operand));
        registerB = result;
    }

    private void out(int operand, List<Integer> output) {
        output.add(operand % 8);
    }

    private void bxc() {
        registerB = registerB ^ registerC;
    }

    private int jnz(int operand, int ip) {
        if (registerA != 0) {
            return operand - 2;
        }
        return ip;
    }

    private void bst(int operand) {
        int result = operand % 8;
        String binaryResult = String.format("%16s", Integer.toBinaryString(result)).replace(' ', '0');
        binaryResult = binaryResult.substring(binaryResult.length()-3);
        registerB = Integer.parseInt(binaryResult,2);
    }

    private void bxl(int operand) {
        registerB = registerB ^ operand;
    }

    private void adv(int operand) {
        registerA = (int)(registerA / Math.pow(2,operand));
    }

    private int getOperandValue(int operand) {
        switch (operand) {
            case 4:
                operand = registerA;
                break;
            case 5:
                operand = registerB;
                break;
            case 6:
                operand = registerC;
                break;
            case 7:
                throw new IllegalArgumentException("Invalid operand number 7");
        }
        return operand;
    }

    public int partTwo() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            program.clear();
            readInput(Path.of("src/main/resources/input.txt"));
            registerA = i;
            String newValue = partOne();
            String programValue = String.join(",", program.stream().map(String::valueOf).toList());
            if (newValue.equals(programValue)) {
                return i;
            }
        }
        return -1;
    }
}
