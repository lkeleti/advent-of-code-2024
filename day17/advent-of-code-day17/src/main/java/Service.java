import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Service {
    private long registerA;
    private long registerB;
    private long registerC;

    private final List<Integer> program = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                registerA = Long.parseLong(line.replace("Register A: ", ""));
                line = br.readLine();
                registerB = Long.parseLong(line.replace("Register B: ", ""));
                line = br.readLine();
                registerC = Long.parseLong(line.replace("Register C: ", ""));
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
        List<Integer> output = compute(registerA);
        return String.join(",", output.stream().map(String::valueOf).toList());
    }

    private List<Integer> compute(Long regA) {
        registerA = regA;
        List<Integer> output = new ArrayList<>();
        for (int ip = 0; ip < program.size(); ip+=2) {
            int opcode = program.get(ip);
            int operand = program.get(ip + 1);

            switch (opcode) {
                case 0:
                    adv(combo(operand));
                    break;
                case 1:
                    bxl(combo(operand));
                    break;
                case 2:
                    bst(combo(operand));
                    break;
                case 3:
                    ip = jnz(combo(operand), ip);
                    break;
                case 4:
                    bxc();
                    break;
                case 5:
                    output.add(out(combo(operand)));
                    break;
                case 6:
                    bdv(combo(operand));
                    break;
                case 7:
                    cdv(combo(operand));
                    break;
            }
        }
        return output;
    }

    private void cdv(long operand) {
        Long result = (registerA / (int)Math.pow(2,operand));
        registerC = result;
    }

    private void bdv(long operand) {
        Long result = (registerA / (int)Math.pow(2,operand));
        registerB = result;
    }

    private int out(long operand) {
        return (int)(operand % 8);
    }

    private void bxc() {
        registerB = registerB ^ registerC;
    }

    private int jnz(long operand, int ip) {
        if (registerA != 0) {
            return (int)operand - 2;
        }
        return ip;
    }

    private void bst(long operand) {
        long result = operand % 8;
        String binaryResult = String.format("%16s", Long.toBinaryString(result)).replace(' ', '0');
        binaryResult = binaryResult.substring(binaryResult.length()-3);
        registerB = Long.parseLong(binaryResult,2);
    }

    private void bxl(long operand) {
        registerB = registerB ^ operand;
    }

    private void adv(long operand) {
        registerA = (registerA / (int)Math.pow(2,operand));
    }

    private long combo(int operand) {
        switch (operand) {
            case 4:
                return registerA;
            case 5:
                return registerB;
            case 6:
                return registerC;
            case 7:
                throw new IllegalArgumentException("Invalid operand number 7");
        }
        return operand;
    }

    public long partTwo() {
        List<Long> candadates = new ArrayList<>();
        candadates.add(0L);

        for (int j = program.size()-1; j >= 0; j--) {
            List<Long> nextCandidates = new ArrayList<>();

            for (Long val: candadates) {
                for (int i = 0; i < 8; i++) {
                    Long target = (val << 3) + i;
                    List<Integer> output = compute(target);
                    List<Integer> sublist = program.subList(j, program.size());
                    if (output.equals(sublist)) {
                        nextCandidates.add(target);
                    }
                }
            }
            candadates.clear();
            candadates.addAll(nextCandidates);
        }
        return candadates.getFirst();
    }
}
/*
1 - 0-7
2 - 8-63
3 - 64-511
4 - 512-4095
5 - 4096-32767
6 - 32768-262143
7 - 262144-2097151
8 - 2097152-16777215
9 - 16777216-134217727
10- 134217728-1073741823
11- 1073741824-8589934591
12- 8589934592-68719476735
13- 68719476736-549755813887
14- 549755813888-4398046511103
15- 4398046511104-35184372088831
16- 35184372088832-281474976710655
*/