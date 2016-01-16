/* Tim Nguyen 2016
LeTwist

LeTwist is a letter twisting game that tests your ability to form valid words
from the given letters. Score points by forming words - the larger the word,
the more points given! Advance through the rounds by finding one of the max
letter words. Keep going until you cannot advance any further.
*/

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.lang.StringBuilder;

public class LeTwist {
    private HashMap<Integer, LinkedList<String>> words; // all possible words

    private int targetWordSize; // maximum word length. default to 7
    private int score; // player's current score
    private Round round; // information for the current round
    private int rounds; // the current round number

    public LeTwist() {
        words = new HashMap<Integer, LinkedList<String>>();

        String wordsFile = "words.txt";
        preprocess(wordsFile);

        targetWordSize = 7; // default
        score = 0;
        rounds = 0;
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

    // TARGET =================================================================
    public String target() {
        /*  Randomly attain a word of the maximum word length to serve as the
            target word. The target word serves as the basis for the rest of
            the words that can be found, since its letters are used. */

        Random random = new Random();

        LinkedList<String> possible = words.get(targetWordSize);
        int index = random.nextInt(possible.size());
        String word = possible.get(index);

        return word;
    }

    // FIND ANAGRAMS ==========================================================
    public HashMap<Integer, LinkedList<String>> findAnagrams(String target) {
        /*  Slight misnomer. Find all words that can be formed from the letters
            of the target word. */

        HashMap<Integer, LinkedList<String>> anagrams = new HashMap<Integer, LinkedList<String>>();

        for (int len : words.keySet()) {
            // Smallest word length will be 3
            if (len > targetWordSize || len <= 2) continue;

            LinkedList<String> possible = words.get(len);

            for (String focus : possible) {
                if (contains(target, focus)) {
                    LinkedList<String> current;

                    if (anagrams.containsKey(len)) current = anagrams.get(len);
                    else current = new LinkedList<String>();

                    current.add(focus);
                    anagrams.put(len, current);
                }
            }
        }

        return anagrams;
    }

    // CONTAINS ===============================================================
    public boolean contains(String s1, String s2) {
        /*  Determine if the second string can be made from the letters in the
            first string */

        HashMap<Character, Integer> letters = new HashMap<Character, Integer>();

        // see what letters the first string contains
        for (int i = 0; i < s1.length(); i++) {
            char focus = s1.charAt(i);

            if (letters.containsKey(focus)) letters.put(focus, letters.get(focus)+1);
            else letters.put(focus, 1);
        }

        // see if letters in second string are contained in first string
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

    // SHUFFLE LETTERS ========================================================
    public void shuffleLetters() {
        /* Shuffle the letters that are displayed */
        round.shuffle();
    }

    // CLEAR SCREEN ===========================================================
    public void clearScreen() {
        /* Clear the terminal screen */

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LeTwist\n\nEnter \"help me\" for help\n");
    }

    // GUESS ==================================================================
    public boolean guess(String guess) {
        /*  Player guesses a word that can be formed from the given letters.
            If it can be, keep track of it and award points. */

        HashMap<String, Boolean> found = round.getFound();

        if (found.containsKey(guess) && !found.get(guess)) {
            // points given is length of word squared times 10
            int points = guess.length() * guess.length() * 10;
            score += points;
            found.put(guess, true);
            return true;
        }

        return false;
    }

    // ROUND DISPLAY ==========================================================
    public void roundDisplay(int timeLeft, boolean end, String message) {
        /*  Display the current state of the game to the player.
            This includes the words that can be formed by the letters, where
            the word is displayed if found or masked with dashes if not.
            Show the letters, score, time left, and round also.
            If this is the end of the round, handle it accordingly */

        HashMap<Integer, LinkedList<String>> anagrams = round.getAnagrams();
        HashMap<String, Boolean> found = round.getFound();
        char[] letters = round.getLetters();

        StringBuilder display = new StringBuilder();

        // Display the possible formed words to the player
        int column = 0;
        for (int key : anagrams.keySet()) {
            LinkedList<String> possible = anagrams.get(key);
            for (String focus : possible) {
                // display the entire word if already found or end of round
                if (found.get(focus)) display.append(focus.toUpperCase());
                else if (end) display.append(focus);
                else { // if not found, mask words with dashes that show length
                    for (int i = 0; i < focus.length(); i++) {
                        display.append("-");
                    }
                }
                display.append("\t");

                // put 6 words in a row for more compact word board
                column++;
                if (column == 6) {
                    display.append("\n");
                    column = 0;
                }
            }
        }
        display.append("\n\n");

        // display the letters that can be used
        for (int i = 0; i < letters.length; i++) {
            display.append(letters[i] + " ");
        }
        display.append("\n\nScore: " + score);

        // Display the time remaining in the round in mm:ss format
        if (end) {
            display.append("\tTime: 00:00");
        } else {
            int minutesLeft = timeLeft / 60;
            int secsLeft = timeLeft % 60;
            display.append("\tTime: 0" + minutesLeft + ":");

            if (secsLeft < 10) display.append("0");
            display.append(secsLeft);
        }

        display.append("\tRound: " + rounds + "\n");

        // message concisely gives the player info
        display.append(message + "\n");

        System.out.println(display.toString());
    }

    // NEW ROUND ==============================================================
    public void newRound() {
        /* Initiate a new round with new letters and words */

        round = new Round(this);
        rounds++;
    }

    // QUALIFY FOR NEXT ROUND =================================================
    public boolean qualifyForNextRound() {
        /*  A player qualifies for the next round only if the player found at
            least one of the maximum letter words. */

        return round.qualifyForNextRound(targetWordSize);
    }

    // CHEAT THE TARGET =======================================================
    public void cheatTheTarget() {
        /*  NOTE: This is a hidden cheat feature
            Automatically guess one of the largest words to pass round */

        String target = round.getTarget();
        guess(target);
    }

    // INSTRUCTIONS ===========================================================
    public void instructions() {
        /* Show the instructions for the game */

        String instruct = "LeTwist: Letter Twisting Fun\nBy Tim Nguyen\n\n"
            + "Instructions:\n"
            + "   Make valid words from the letters provided.\n"
            + "   Score points for each word found.\n"
            + "   Advance to next round by finding one of the max length words.\n"
            + "   You have 2 minutes per round to get as many words as you can!\n"
            + "\nPossible Actions to Enter:\n"
            + "   A word that can be made out of the letters\n"
            + "   \"next round\" - to go to the next round\n"
            + "   \"exit game\" - to exit the game\n"
            + "   \"shuffle me\" - to shuffle your letters\n"
            + "   \"help me\" - to show this help dialogue\n";

        System.out.println(instruct);
    }
}
