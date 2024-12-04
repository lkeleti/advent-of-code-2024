import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    public String memoryDump = "";
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                memoryDump += line;
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        String text = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        return getTotal(memoryDump);
    }

    private Long getTotal(String text) {
        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        Long total = 0L;
        while (matcher.find()) {
            String mul = matcher.group();
            mul = mul.replace("mul(","");
            mul = mul.replace(")","");
            Long number1 = Long.parseLong(mul.split(",")[0]);
            Long number2 = Long.parseLong(mul.split(",")[1]);
            Long multipliedValue = number1 * number2;
            total +=  multipliedValue;

        }
        return total;
    }

    public long partTwo() {
        String text = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
        text = getString(memoryDump);
        return getTotal(text);
    }

    private static String getString(String text) {
        int start = 0;
        int end;
        while (start != -1) {
            start = text.indexOf("don't()");
            if (start != -1) {
                String temp = text.substring(0, start);
                end = text.indexOf("do()", start);
                if (end != -1) {
                    temp += text.substring(end + 4);
                }
                text = temp;
            }
        }
        return text;
    }
}
