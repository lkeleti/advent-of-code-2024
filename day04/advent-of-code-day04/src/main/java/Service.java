import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public final List<List<Character>> board = new ArrayList<>();
    private int rowMax = 0;
    private int colMax = 0;

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char c: line.toCharArray()) {
                    row.add(c);
                }
                board.add(row);
            }
            rowMax = board.size();
            colMax = board.getFirst().size();
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        int counter = 0;
        for (int j = 0; j < colMax; j++) {
            for (int i = 0; i < rowMax; i++) {
                counter += findWord("XMAS", i, j);
            }
        }
        return counter;
    }

    private int findWord(String word, int i, int j) {
        int counter = 0;
        //down
        String defWord = "";
        for (int row = i; row < Math.min(rowMax, i + word.length()); row++) {
            defWord += board.get(row).get(j);
        }
        if (defWord.equals(word)) {
            counter++;
        }
        if (backward(defWord).equals(word)) {
            counter++;
        }

        //right
        defWord = "";
        for (int col = j; col < Math.min(colMax, j + word.length()); col++) {
            defWord += board.get(i).get(col);
        }
        if (defWord.equals(word)) {
            counter++;
        }
        if (backward(defWord).equals(word)) {
            counter++;
        }

        //diag down-right
        defWord = "";
        for (int k = 0; k < word.length(); k++) {
            if (i+k < rowMax && j+k < colMax) {
                defWord += board.get(i+k).get(j+k);
            }
        }
        if (defWord.equals(word)) {
            counter++;
        }
        if (backward(defWord).equals(word)) {
            counter++;
        }

        //diag up
        defWord = "";
        for (int k = 0; k < word.length(); k++) {
            if (i+k < rowMax && j-k >= 0) {
                defWord += board.get(i+k).get(j-k);
            }
        }
        if (defWord.equals(word)) {
            counter++;
        }
        if (backward(defWord).equals(word)) {
            counter++;
        }
        return counter;
    }

    private String backward(String wordToBackward) {
        String newWord = "";
        for (char c: wordToBackward.toCharArray()) {
            newWord = c + newWord;
        }
        return newWord;
        //2544 too low
    }

    public int partTwo() {
        return 0;
    }
}
