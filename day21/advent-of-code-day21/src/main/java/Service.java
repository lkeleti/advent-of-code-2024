import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private final List<String> unlockCodes = new ArrayList<>();
    private final List<List<Character>> numericKeypad = new ArrayList<>();
    private final List<List<Character>> directionalKeypad = new ArrayList<>();
    private final Node up = new Node(List.of('^'), new Cord(0,-1));
    private final Node right = new Node(List.of('>'), new Cord(1,0));
    private final Node down = new Node(List.of('V'), new Cord(0,1));
    private final Node left = new Node(List.of('<'), new Cord(-1,0));
    private final List<Node> directions = List.of(right, down, up, left );

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                unlockCodes.add(line);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
        init();
    }

    private void init() {
        numericKeypad.add(List.of('7','8','9'));
        numericKeypad.add(List.of('4','5','6'));
        numericKeypad.add(List.of('1','2','3'));
        numericKeypad.add(List.of('#','0','A'));

        directionalKeypad.add(List.of('#','^','A'));
        directionalKeypad.add(List.of('<','V','>'));
    }

    public long partOne() {
        for (String code: unlockCodes) {
            List<List<String>> allPaths = new ArrayList<>();
            simulateNumpad(code, allPaths);
            List<String> numpadCombinations = combineAllPaths(allPaths);
            System.out.println();
        }
        return 0;
    }

    private void simulateNumpad(String code, List<List<String>> allPaths) {
        char startValue = 'A';
        for (int i = 0; i < code.length(); i++ ) {
            char endValue = code.charAt(i);
            List<String> paths = findAllPaths(numericKeypad, startValue, endValue);
            int minMoves = paths.stream().mapToInt(String::length).min().orElse(-1);
            allPaths.add(paths.stream().filter(s -> s.length() == minMoves).toList());
            startValue = endValue;
        }
    }

    private List<String> combineAllPaths(List<List<String>> allPaths) {
        List<String> combinations = new ArrayList<>(); 
        combineRecursive(allPaths, 0, "", combinations); 
        return combinations;
    }

    private void combineRecursive(List<List<String>> allPaths, int depth, String current, List<String> combinations) {
        if (depth == allPaths.size()) { combinations.add(current); return; } for (String path : allPaths.get(depth)) { combineRecursive(allPaths, depth + 1, current + path, combinations); }
    }

    private List<String> findAllPaths(List<List<Character>> keypad, char startValue, char endValue) {
        List<String> paths = new ArrayList<>();
        Cord startPos = findValue(startValue, keypad);
        Set<Cord> newHashSet = new HashSet<>();
        newHashSet.add(startPos);
        findPaths(keypad, startValue, endValue, "", paths, newHashSet);
        return paths;
    }

    private void findPaths(List<List<Character>> keypad, char startValue, char endValue, String path, List<String> paths, Set<Cord> seen) {
        int maxPosX = keypad.getFirst().size() - 1;
        int maxPosY = keypad.size() - 1;
        Cord startPos = findValue(startValue, keypad);

        if (startValue == endValue) {
            paths.add(path);
            return;
        }

        for (Node direction: directions) {
            int nextPosX = startPos.getPosX() + direction.getPosition().getPosX();
            int nextPosY = startPos.getPosY() + direction.getPosition().getPosY();

            if (!(nextPosX < 0 || nextPosY < 0 || nextPosX > maxPosX || nextPosY > maxPosY)) {
                startValue = keypad.get(nextPosY).get(nextPosX);
                if (startValue != '#') {
                    Cord nextPos = new Cord(nextPosX, nextPosY);
                    if (!seen.contains(nextPos)) {
                        seen.add(nextPos);
                        findPaths(keypad, startValue, endValue, path + direction.getDirection().getFirst(), paths, new HashSet<>(seen));
                    }
                }
            }
        }
    }

    private Cord findValue(Character valueToFind, List<List<Character>> board) {
        int maxX = board.getFirst().size();
        int maxY = board.size();
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (board.get(y).get(x).equals(valueToFind)) {
                    return new Cord(x,y);
                }
            }
        }
        return new Cord(-1,-1);
    }

    public int partTwo() {
        return 0;
    }
}
