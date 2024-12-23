import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private final Map<String, List<String>> nodes = new HashMap<>();
    private final List<Triplet> triplets = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split("-");
                if (!nodes.containsKey(datas[0])) {
                    nodes.put(datas[0], new ArrayList<>());
                }
                nodes.get(datas[0]).add(datas[1]);
                if (!nodes.containsKey(datas[1])) {
                    nodes.put(datas[1], new ArrayList<>());
                }
                nodes.get(datas[1]).add(datas[0]);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        for (Map.Entry<String, List<String>> node: nodes.entrySet()) {
            for (String subNode: node.getValue()) {
                if (nodes.containsKey(subNode)) {
                    for (String subSubNode : nodes.get(subNode)) {
                        if (nodes.containsKey(subSubNode)) {
                            if (nodes.get(subSubNode).contains(node.getKey())) {
                                triplets.add(new Triplet(node.getKey(), subNode, subSubNode));
                            }
                        }
                    }
                }
            }
        }
        return triplets.stream()
                .filter(triplet -> triplet.getName1().startsWith("t") ||  triplet.getName2().startsWith("t") || triplet.getName3().startsWith("t"))
                .toList().size()/6;
    }
    public int partTwo() {
        return 0;
    }
}
