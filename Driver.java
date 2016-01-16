/* Tim Nguyen 2016

The driver is what the human player will interact with to play the game.
It drives the game. This helps to modularize the game classes from the main
function.
*/

import java.util.Scanner;

public class Driver {
    // MAIN ===================================================================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeTwist game = new LeTwist();
        game.clearScreen();
        game.instructions();
        System.out.println("Press Enter to Begin");
        scanner.nextLine();

        boolean playAgain = true;

        while (playAgain) {
            do {
                String msg = "";
                game.newRound();
                int startTime = (int)System.currentTimeMillis() / 1000;
                int currentTime = (int)System.currentTimeMillis() / 1000;

                while (currentTime - startTime < 120) {
                    game.clearScreen();

                    int timeLeft = 120 - (currentTime - startTime);
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
