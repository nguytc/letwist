import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.lang.StringBuilder;

public class LeTwist {
    private HashMap<Integer, LinkedList<String>> words;
    private int targetWordSize;
    private int score;
    private Round round;
    private int rounds;

    public LeTwist() {
        words = new HashMap<Integer, LinkedList<String>>();

        String wordsFile = "words.txt";
        preprocess(wordsFile);

        targetWordSize = 7;
        score = 0;
        rounds = 0;
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

    public HashMap<Integer, LinkedList<String>> findAnagrams(String target) {
        HashMap<Integer, LinkedList<String>> anagrams = new HashMap<Integer, LinkedList<String>>();
        for (int len : words.keySet()) {
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

    public void shuffleLetters() {
        
    }

    // Fisher–Yates shuffle
    public char[] shuffle(String s) {
        char[] letters = s.toCharArray();

        Random random = new Random();
        for (int i = letters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            char tmp = letters[index];
            letters[index] = letters[i];
            letters[i] = tmp;
        }

        return letters;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LeTwist\n\nEnter \"help me\" for help\n");
    }

    public int getScore() {
        return score;
    }

    public boolean guess(String guess) {
        HashMap<String, Boolean> found = round.getFound();

        if (found.containsKey(guess) && !found.get(guess)) {
            int points = guess.length() * guess.length() * 10;
            score += points;
            found.put(guess, true);
            return true;
        }

        return false;
    }

    public void roundDisplay(int timeLeft, boolean end, String message) {
        HashMap<Integer, LinkedList<String>> anagrams = round.getAnagrams();
        HashMap<String, Boolean> found = round.getFound();
        char[] letters = round.getLetters();

        StringBuilder display = new StringBuilder();

        int column = 0;
        for (int key : anagrams.keySet()) {
            LinkedList<String> possible = anagrams.get(key);
            for (String focus : possible) {
                if (found.get(focus)) display.append(focus);
                else if (end) display.append(focus.toUpperCase());
                else {
                    for (int i = 0; i < focus.length(); i++) {
                        display.append("-");
                    }
                }
                display.append("\t");

                column++;
                if (column == 6) {
                    display.append("\n");
                    column = 0;
                }
            }
        }
        display.append("\n\n");

        for (int i = 0; i < letters.length; i++) {
            display.append(letters[i] + " ");
        }
        display.append("\n\nScore: " + score);

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
        display.append(message + "\n");

        System.out.println(display.toString());
    }

    public void newRound() {
        round = new Round(this);
        rounds++;
    }

    public boolean qualifyForNextRound() {
        return round.qualifyForNextRound();
    }

    public void cheatTheTarget() {
        String target = round.getTarget();
        guess(target);
    }

    public void printHelp() {
        String display = "\nEnter:\n" +
                        "guess - a word that can be made out of the letters\n" +
                        "\"next round\" - to go to the next round\n" +
                        "\"exit game\" - to exit the game\n" +
                        "\"shuffle me\" - to shuffle your letters\n" +
                        "\"help me\" - to show this help dialogue\n";

        System.out.println(display);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeTwist game = new LeTwist();

        do {
            String msg = "";
            game.newRound();
            int startTime = (int)System.currentTimeMillis() / 1000;
            int currentTime = (int)System.currentTimeMillis() / 1000;

            while (currentTime - startTime < 120) {
                game.clearScreen();

                int timeLeft = 120 - (currentTime - startTime);
                game.roundDisplay(timeLeft, false, msg);


                while (true) {
                    String input = scanner.nextLine().toLowerCase();

                    if (input.equals("next round")) {
                        break;
                    } else if (input.equals("shuffle me")) {
                        msg = "Shuffled";
                    } else if (input.equals("exit game")) {
                        game.clearScreen();
                        game.roundDisplay(0, true, "Exiting the game");
                        scanner.nextLine();
                        return;
                    } else if (input.equals("impossible word")) {
                        game.cheatTheTarget();
                        msg = "You filthy cheater";
                    } else if (input.equals("help me")) {
                        game.printHelp();
                        continue;
                    } else {
                        boolean result = game.guess(input);
                        if (result) msg = "Nice";
                        else msg = "";
                    }
                    break;
                }

                currentTime = (int)System.currentTimeMillis() / 1000;
            }

            game.clearScreen();
            if (game.qualifyForNextRound()) msg = "You qualify for the next round!";
            else msg = "Game over";
            game.roundDisplay(0, true, msg);
            scanner.nextLine();

        } while(game.qualifyForNextRound());
    }
}
