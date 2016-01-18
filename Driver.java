/* Tim Nguyen 2016

The driver is what the human player will interact with to play the game.
It drives the game. This helps to modularize the game classes from the main
function.
*/

import java.util.Scanner;

public class Driver {
    // SETTINGS ===============================================================
    public static void settings(Scanner scanner, LeTwist game) {
        /* Allow the user to set the maximum word length and time per round */

        // Set the maximum word length
        System.out.println("\nMaximum word length? Enter 6 to 10");
        String in = scanner.nextLine();

        int maxLength = Integer.parseInt(in);
        if (maxLength < 6 || maxLength > 10) System.out.println("Invalid");
        else game.setTargetWordSize(maxLength);

        System.out.println("Max word length will be " + game.getTargetWordSize() + "\n");

        // Set the time per round
        System.out.println("Minutes per round? Enter 1 to 9");
        in = scanner.nextLine();

        int minPerRound = Integer.parseInt(in);
        if (minPerRound < 1 || minPerRound > 9) System.out.println("Invalid");
        else game.setMinutesPerRound(minPerRound);

        System.out.println("Minutes per round will be " + game.getMinutesPerRound() + "\n");
    }

    // MAIN ===================================================================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeTwist game = new LeTwist();
        game.clearScreen();
        game.instructions();

        String in;
        do {
            System.out.println("Press Enter to begin");
            // handle the settings here
            in = scanner.nextLine().toLowerCase();
            if (in.equals("settings")) settings(scanner, game);
        } while(in.equals("settings"));

        int minPerRound = game.getMinutesPerRound();
        int secPerRound = minPerRound * 60;

        boolean playAgain = true;

        while (playAgain) {
            do {
                String msg = "";
                game.newRound();
                int startTime = (int)System.currentTimeMillis() / 1000;
                int currentTime = (int)System.currentTimeMillis() / 1000;

                while (currentTime - startTime < secPerRound) {
                    game.clearScreen();

                    int timeLeft = secPerRound - (currentTime - startTime);
                    game.roundDisplay(timeLeft, false, msg);

                    boolean nextRound = false;
                    while (true) {
                        String input = scanner.nextLine().toLowerCase();

                        if (input.equals("next round")) {
                            nextRound = true;
                        } else if (input.equals("shuffle me")) {
                            game.shuffleLetters();
                            msg = "Shuffled";
                        } else if (input.equals("exit game")) {
                            game.clearScreen();
                            game.roundDisplay(0, true, "Exiting the game");
                            return;
                        } else if (input.equals("impossible word")) {
                            game.cheatTheTarget();
                            msg = "You filthy cheater";
                        } else if (input.equals("help me")) {
                            game.instructions();
                            continue;
                        } else {
                            boolean result = game.guess(input);
                            if (result) msg = "Nice";
                            else msg = "";
                        }
                        break;
                    }

                    if (nextRound) break;
                    currentTime = (int)System.currentTimeMillis() / 1000;
                }

                game.clearScreen();
                if (game.qualifyForNextRound()) msg = "You qualify for the next round!";
                else msg = "Game over";
                game.roundDisplay(0, true, msg);
                scanner.nextLine();

            } while(game.qualifyForNextRound());

            System.out.println("Enter \"play again\" to play again");
            String again = scanner.nextLine().toLowerCase();
            if (!again.equals("play again")) playAgain = false;
        }
    }
}
