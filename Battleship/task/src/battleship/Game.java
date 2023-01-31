package battleship;

import battleship.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final List<Player> players;
    private boolean hasWinner;

    public Game() {
        players = new ArrayList<>();

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        player1.setFoesBattlefield(player2.getMyBattlefield());
        player2.setFoesBattlefield(player1.getMyBattlefield());

        players.add(player1);
        players.add(player2);

        hasWinner = false;
    }

    public void play() {
        for (Player player : this.players) {
            player.positionShipsOnTheBattlefield();
            promptEnterKey();
        }

        do {
            for (Player player : players) {
                hasWinner = player.shoot();
                if (hasWinner) {
                    break;
                }
                promptEnterKey();
            }
        } while (!hasWinner);

        end();
    }

    private static void end() {
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private static void promptEnterKey() {
        String keyPressed;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Press Enter and pass the move to another player");
            keyPressed = scanner.nextLine();

        } while (!keyPressed.equals(""));
    }
}
