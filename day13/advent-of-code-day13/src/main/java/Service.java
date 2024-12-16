import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Service {

    private final List<ClawMachine> clawMachines = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                Pattern r = Pattern.compile("\\d+");
                Matcher m = r.matcher(line);
                m.find();
                int ax = Integer.parseInt(m.group());
                m.find();
                int ay = Integer.parseInt(m.group());

                line = br.readLine();
                m = r.matcher(line);
                m.find();
                int bx = Integer.parseInt(m.group());
                m.find();
                int by = Integer.parseInt(m.group());

                line = br.readLine();
                m = r.matcher(line);
                m.find();
                int px = Integer.parseInt(m.group());
                m.find();
                int py = Integer.parseInt(m.group());
                line = br.readLine();
                clawMachines.add(new ClawMachine(ax, ay, bx, by, px, py));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        long total = 0;
        for (ClawMachine clawMachine: clawMachines) {
            long s = 0;
            long t = 0;
            long a = (clawMachine.getPx() * clawMachine.getBy() - clawMachine.getPy() * clawMachine.getBx());
            long b = (clawMachine.getAx() * clawMachine.getBy() - clawMachine.getAy() * clawMachine.getBx());
            if (a % b == 0) {
                s = a / b;
                a = (clawMachine.getPx() - (s * clawMachine.getAx()));
                b = clawMachine.getBx();
                if (a % b == 0) {
                    t = a / b;
                }
            }
            total += (3*s)+t;
        }
        return total;
    }
    public long partTwo() {
        long total = 0;
        for (ClawMachine clawMachine: clawMachines) {
            long s = 0;
            long t = 0;
            long a = ((clawMachine.getPx() + 10000000000000L) * clawMachine.getBy() - (clawMachine.getPy() + 10000000000000L) * clawMachine.getBx());
            long b = (clawMachine.getAx() * clawMachine.getBy() - clawMachine.getAy() * clawMachine.getBx());
            if (a % b == 0) {
                s = a / b;
                a = ((clawMachine.getPx()+10000000000000L) - (s * clawMachine.getAx()));
                b = clawMachine.getBx();
                if (a % b == 0) {
                    t = a / b;
                } else {
                    s = 0;
                    t = 0;
                }
            }
            total += (3*s)+t;
        }
        return total;
        //71493195288213 high
    }
}
