import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private final List<String> unlockCodes = new ArrayList<>();
    private final List<List<Character>> numericKeypad = new ArrayList<>();
    private final List<List<Character>> directionalKeypad = new ArrayList<>();
    private final Cord numericStartPos = new Cord(2,3);
    private final Cord directionalStartPos = new Cord(2,0);
    private final Node up = new Node(List.of('^'), new Cord(0,-1));
    private final Node right = new Node(List.of('>'), new Cord(1,0));
    private final Node down = new Node(List.of('V'), new Cord(0,1));
    private final Node left = new Node(List.of('<'), new Cord(-1,0));
    private final List<Node> directions = List.of(right, down, up, left );

    private final int numericMaxX = 3;
    private final int numericMaxY = 4;

    private final int directionalMaxX = 3;
    private final int directionalMaxY = 2;

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
        List<String> numericMoves = new ArrayList<>();
        List<String> directionalMoves1 = new ArrayList<>();
        List<String> directionalMoves2 = new ArrayList<>();
        simulateNumpad(numericMoves);
        System.out.println(numericMoves.get(0));

        simulateDirectionpad(numericMoves, directionalMoves1);
        System.out.println(directionalMoves1.get(0));

        simulateDirectionpad(directionalMoves1, directionalMoves2);
        System.out.println(directionalMoves2.get(0).length());
        System.out.println(directionalMoves2.get(1).length());
        System.out.println(directionalMoves2.get(2).length());
        System.out.println(directionalMoves2.get(3).length());
        System.out.println(directionalMoves2.get(4).length());
        return 0;
    }

    private void simulateDirectionpad(List<String> numericMoves, List<String> directionalMoves1) {
        for (String code: numericMoves) {
            List<Character> moves = new ArrayList<>();
            moves.addAll(bfs(directionalKeypad, 'A', code.toCharArray()[0]).getFirst().getDirection());
            moves.add('A');
            for (int i = 0; i < code.length()-1; i++ ) {
                moves.addAll(bfs(directionalKeypad, code.toCharArray()[i], code.toCharArray()[i+1]).getFirst().getDirection());
                moves.add('A');
            }
            directionalMoves1.add(moves.toString().replace(", ","").replace("[","").replace("]",""));
        }
    }

    private void simulateNumpad(List<String> numericMoves) {
        for (String code: unlockCodes) {
            List<Character> moves = new ArrayList<>();
            moves.addAll(bfs(numericKeypad, 'A', code.toCharArray()[0]).getFirst().getDirection());
            moves.add('A');
            for (int i = 0; i < code.length()-1; i++ ) {
                moves.addAll(bfs(numericKeypad, code.toCharArray()[i], code.toCharArray()[i+1]).getFirst().getDirection());
                moves.add('A');
            }
            numericMoves.add(moves.toString().replace(", ","").replace("[","").replace("]",""));
        }
    }

    private List<Node> bfs(List<List<Character>> board, Character startValue, Character endValue) {
        List<Node> result = new ArrayList<>();
        int maxPosX = board.getFirst().size() - 1;
        int maxPosY = board.size() - 1;
        int shortest = -1;
        Cord startPos = findValue(startValue, board);
        Queue<Node> queue = new ArrayDeque<>();
        Set<Cord> seen = new HashSet<>();

        if (board.get(startPos.getPosY()).get(startPos.getPosX()).equals(endValue)) {
            result.add(new Node(new ArrayList<>(),new Cord(-1,-1)));
            return result;
        }

        queue.add(new Node(new ArrayList<>(),startPos));
        seen.add(startPos);

        while (!queue.isEmpty()){
            Node currentNode = queue.poll();
            int currentPosX = currentNode.getPosition().getPosX();
            int currentPosY = currentNode.getPosition().getPosY();

            for (Node direction: directions) {
                int nextPosX = currentPosX + direction.getPosition().getPosX();
                int nextPosY = currentPosY + direction.getPosition().getPosY();

                if (!(nextPosX < 0 || nextPosY < 0 || nextPosX > maxPosX || nextPosY > maxPosY)) {
                    Cord newCord = new Cord(nextPosX, nextPosY);
                    Node newNode = new Node(currentNode.copyDirections(), newCord);
                    newNode.getDirection().add(direction.getDirection().getFirst());

                    if (board.get(nextPosY).get(nextPosX).equals(endValue)) {
                        if (shortest == -1) {
                            shortest = newNode.getDirection().size();
                        } else if (newNode.getDirection().size() > shortest) {
                            continue;
                        }
                        result.add(newNode);
                        continue;
                    }
                    if (board.get(nextPosY).get(nextPosX) != '#') {
                        if (!seen.contains(newCord)) {
                            queue.add(newNode);
                            seen.add(newCord);
                        }
                    }
                }

            }
        }
        return result;
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
