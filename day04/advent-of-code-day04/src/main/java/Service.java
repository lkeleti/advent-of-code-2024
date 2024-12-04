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
        counter += checkWords(word, defWord);

        //right
        defWord = "";
        for (int col = j; col < Math.min(colMax, j + word.length()); col++) {
            defWord += board.get(i).get(col);
        }
        counter += checkWords(word, defWord);

        //diag down-right
        defWord = "";
        for (int k = 0; k < word.length(); k++) {
            if (i+k < rowMax && j+k < colMax) {
                defWord += board.get(i+k).get(j+k);
            }
        }
        counter += checkWords(word, defWord);

        //diag up
        defWord = "";
        for (int k = 0; k < word.length(); k++) {
            if (i+k < rowMax && j-k >= 0) {
                defWord += board.get(i+k).get(j-k);
            }
        }
        counter += checkWords(word, defWord);
        return counter;
    }

    private int checkWords(String word, String defWord) {
        if (defWord.equals(word)) {
            return 1;
        }
        if (backward(defWord).equals(word)) {
            return 1;
        }
        return 0;
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
        int counter = 0;
        for (int j = 0; j < colMax-2; j++) {
            for (int i = 0; i < rowMax-2; i++) {
                counter += findWord2("MAS", i, j);
            }
        }
        return counter;
    }

    private int findWord2(String word, int i, int j) {
        String word1 = "";
        String word2 = "";

        word1 += board.get(i).get(j);
        word1 += board.get(i+1).get(j+1);
        word1 += board.get(i+2).get(j+2);

        word2 += board.get(i+2).get(j);
        word2 += board.get(i+1).get(j+1);
        word2 += board.get(i).get(j+2);

        if ((checkWords(word,word1) != 0) && (checkWords(word,word2) != 0)) {
            return 1;
        }
        return 0;
    }
}
