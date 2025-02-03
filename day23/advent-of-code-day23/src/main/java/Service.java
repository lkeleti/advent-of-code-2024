import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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

    private List<String> fingLargestClique() {
        List<String> maxClique = new ArrayList<>();
        for (String computer1: nodes.keySet()) {
            for (String computer2: nodes.get(computer1)) {
                List<String> currentComputers = new ArrayList<>();
                List<String> currentClique = new ArrayList<>();
                if (!(computer1.equals(computer2))) {
                    currentClique.add(computer1 + "-" + computer2);
                    currentComputers.add(computer1);
                    currentComputers.add(computer2);
                }
                for (String computer3: nodes.keySet()) {
                    if (!currentComputers.contains(computer3)) {
                        boolean isConnectedToAll = true;
                        for (String connectedComputer: currentComputers) {
                            if (!nodes.get(connectedComputer).contains(computer3)) {
                                isConnectedToAll = false;
                                break;
                            }
                        }
                        if (isConnectedToAll) {
                            currentClique.add(computer1 + "-" + computer3);
                            currentClique.add(computer2 + "-" + computer3);
                            currentComputers.add(computer3);
                        }
                    }
                }

                if (currentClique.size() > maxClique.size()) {
                    maxClique = currentClique;
                }
            }
        }
        return maxClique;
    }
    public int partTwo() {
        List<String> maxClique = fingLargestClique();
        Set<String> cliqueComputers = new TreeSet<>();
        for (String connection : maxClique) {
            String[] computers = connection.split("-");
            cliqueComputers.add(computers[0]);
            cliqueComputers.add(computers[1]);
        }
        System.out.println(String.join(",", cliqueComputers));
        return cliqueComputers.size();
    }
}
