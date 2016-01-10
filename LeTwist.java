import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class LeTwist {
    private HashMap<Integer, LinkedList<String>> words;
    private int targetWordSize;

    public LeTwist() {
        words = new HashMap<Integer, LinkedList<String>>();

        String wordsFile = "words.txt";
        preprocess(wordsFile);

        targetWordSize = 7;
    }

    public void preprocess(String wordsFile) {
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

    public String target() {
        Random random = new Random();

        LinkedList<String> possible = words.get(targetWordSize);
        int index = random.nextInt(possible.size());
        String word = possible.get(index);

        return word;
    }

    public HashMap<Integer, LinkedList<String>> getAnagrams(String target) {
        HashMap<Integer, LinkedList<String>> anagrams = new HashMap<Integer, LinkedList<String>>();
        for (int len : words.keySet()) {
            if (len > targetWordSize || len <= 2) continue;

            LinkedList<String> possible = words.get(len);
            System.out.println(possible.size());

            for (String focus : possible) {
                if (contains(target, focus)) {
                    if (anagrams.containsKey(len)) {
                        LinkedList<String> current;

                        if (anagrams.containsKey(len)) current = anagrams.get(len);
                        else current = new LinkedList<String>();

                        current.add(focus);
                        anagrams.put(len, current);
                    }
                }
            }
        }

        return anagrams;
    }

    public boolean contains(String s1, String s2) {
        /*  See if the second string can be made from the letters in the first
            string */

        HashMap<Character, Integer> letters = new HashMap<Character, Integer>();

        // see what letters the first string contains
        for (int i = 0; i < s1.length(); i++) {
            char focus = s1.charAt(i);

            if (letters.containsKey(focus)) letters.put(focus, letters.get(focus)+1);
            else letters.put(focus, 1);
        }

        for (int i = 0; i < s2.length(); i++) {
            char focus = s2.charAt(i);

            if (!letters.containsKey(focus) || letters.get(focus) <= 0) {
                return false;
            } else {
                letters.put(focus, letters.get(focus)-1);
            }
        }

        return true;
    }

    public static void main(String[] args) {
        LeTwist game = new LeTwist();

        String target = game.target();
        HashMap<Integer, LinkedList<String>> anagrams = game.getAnagrams(target);

        System.out.println("TARGET: " + target);
        for (int key : anagrams.keySet()) {
            System.out.println("TESTING HERE");
            LinkedList<String> possible = anagrams.get(key);
            for (String focus : possible) {
                System.out.println(focus);
            }
        }
    }
}
