import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;

public class Round {
    private String target; // target word
    private char[] letters; // letters in target word shuffled

    // words that can be formed with letters of the target word
    private HashMap<Integer, LinkedList<String>> anagrams;
    private HashMap<String, Boolean> found; // keeps track of words found

    public Round(LeTwist game) {
        target = game.target();
        shuffle();

        anagrams = game.findAnagrams(target);
        found = new HashMap<String, Boolean>();

        // key in found are the words. value is false if not found yet, or true
        // if it has been found
        for (int key : anagrams.keySet()) {
            LinkedList<String> possible = anagrams.get(key);
            for (String focus : possible) found.put(focus, false);
        }
    }

    // GETTERS ================================================================
    public String getTarget() {
        return target;
    }

    public char[] getLetters() {
        return letters;
    }

    public HashMap<Integer, LinkedList<String>> getAnagrams() {
        return anagrams;
    }

    public HashMap<String, Boolean> getFound() {
        return found;
    }

    // SHUFFLE ================================================================
    public void shuffle() {
        /*  Perform the Fisher-Yates shuffle to shuffle the letters of the
            target word to be displayed to the player. */
        char[] shuffled = target.toCharArray();

        Random random = new Random();
        for (int i = shuffled.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            char tmp = shuffled[index];
            shuffled[index] = shuffled[i];
            shuffled[i] = tmp;
        }

        letters = shuffled;
    }

    // QUALIFY FOR NEXT ROUND =================================================
    public boolean qualifyForNextRound(int targetWordSize) {
        /*  A player qualifies for the next round only if the player found at
            least one of the maximum letter words. */

        LinkedList<String> needed = anagrams.get(targetWordSize);

        for (String word : needed) {
            if (found.get(word)) return true;
        }

        return false;
    }
}
