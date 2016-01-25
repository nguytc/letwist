import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Words {
    private HashMap<Integer, LinkedList<String>> words; // all possible words
    private String wordsFile;

    public Words() {
        words = new HashMap<Integer, LinkedList<String>>();

        wordsFile = "../resources/words.txt";
        preprocess(wordsFile);
    }

    // PREPROCESS =============================================================
    public void preprocess(String wordsFile) {
        /*  Obtain the words from the list of words given in the file.
            Store them in a hashmap called words. The key will be the length of
            the words and the value will be a linked list of the words with the
            key as the length. */

        try (BufferedReader br = new BufferedReader(new FileReader(wordsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                int len = line.length();
                LinkedList<String> current;

                if (words.containsKey(len)) current = words.get(len);
                else current = new LinkedList<String>();

                current.add(line);
                words.put(len, current);
            }
        } catch (Exception e) {}
    }

    // RANDOM WORD ============================================================
    public String randomWord(int length) {
        /* return a random word of the given length */

        if (length < 2 || length > 10) return "";

        Random random = new Random();

        LinkedList<String> possible = words.get(length);
        int index = random.nextInt(possible.size());
        String word = possible.get(index);

        return word;
    }

    // GETTERS ================================================================
    public HashMap<Integer, LinkedList<String>> getWords() {
        return words;
    }

    public String getWordsFile() {
        return wordsFile;
    }
}
