import java.util.LinkedList;
import java.util.HashMap;

public class Round {
    private String target;
    private char[] letters;
    private HashMap<Integer, LinkedList<String>> anagrams;
    private HashMap<String, Boolean> found;

    public Round(LeTwist game) {
        target = game.target();
        letters = game.shuffle(target);
        anagrams = game.findAnagrams(target);
        found = new HashMap<String, Boolean>();

        for (int key : anagrams.keySet()) {
            LinkedList<String> possible = anagrams.get(key);
            for (String focus : possible) found.put(focus, false);
        }
    }

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

    public boolean qualifyForNextRound() {
        LinkedList<String> needed = anagrams.get(7);

        for (String word : needed) {
            if (found.get(word)) return true;
        }

        return false;
    }
}
