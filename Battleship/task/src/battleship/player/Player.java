package battleship.player;

import battleship.battlefield.Battlefield;
import battleship.constants.Constants;
import battleship.coordinate.Coordinate;
import battleship.ship.Ship;
import battleship.ship.ShipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private final String name;
    private final Battlefield myBattlefield;
    private Battlefield foesBattlefield;
    private final Battlefield foesBlankBattlefield;
    private final List<Ship> ships;
    private boolean hasWon;

    public Player(String name) {
        this.name = name;
        hasWon = false;
        ships = new ArrayList<>();
        myBattlefield = new Battlefield();
        foesBattlefield = new Battlefield();
        foesBlankBattlefield = new Battlefield();

        for (ShipType shipType : ShipType.values()) {
            ships.add(new Ship(shipType.getName(), shipType.getSize()));
        }
    }

    public void positionShipsOnTheBattlefield() {
        System.out.printf("%s, place your ships on the game field", name);
        System.out.println();
        myBattlefield.printToConsole();

        for (Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", ship.getShipClass(), ship.getSize());
            System.out.println();

            boolean isShipPositionedCorrectly;

            do {
                Scanner scanner = new Scanner(System.in);

                String unformattedStartCoordinates = scanner.next();
                String unformattedEndCoordinates   = scanner.next();

                Coordinate startCoordinates = new Coordinate();
                startCoordinates.setCoordinates(unformattedStartCoordinates);

                Coordinate endCoordinates = new Coordinate();
                endCoordinates.setCoordinates(unformattedEndCoordinates);

                if (startCoordinates.areInvalid() || endCoordinates.areInvalid()) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    isShipPositionedCorrectly = false;
                } else {
                    isShipPositionedCorrectly = myBattlefield.positionShip(ship, startCoordinates, endCoordinates);
                }

            } while (!isShipPositionedCorrectly);

            myBattlefield.printToConsole();
        }
    }

    public boolean shoot() {
        foesBlankBattlefield.printToConsole();
        System.out.println(Constants.DIVIDER);
        myBattlefield.printToConsole();

        System.out.printf("%s, it's your turn:", name);
        System.out.println();

        Coordinate coordinates = new Coordinate();

        do {
            Scanner scanner = new Scanner(System.in);

            String unformattedShootingCoordinate = scanner.next();

            coordinates.setCoordinates(unformattedShootingCoordinate);

            if (coordinates.areInvalid()) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }

        } while (coordinates.areInvalid());

        boolean shipHit = foesBattlefield.hitShip(coordinates);

        if (shipHit) {
            foesBattlefield.registerHit(coordinates);
            foesBattlefield.removeHitShipCoordinate(coordinates);
            foesBlankBattlefield.registerHit(coordinates);
        } else {
            foesBattlefield.registerMiss(coordinates);
            foesBlankBattlefield.registerMiss(coordinates);
            System.out.println("You missed!");
        }

        foesBlankBattlefield.printToConsole();
        System.out.println(Constants.DIVIDER);
        myBattlefield.printToConsole();

        if (!foesBattlefield.hasShips()) {
            hasWon = true;
        }

        return hasWon;
    }

    public void setFoesBattlefield(Battlefield battlefield) {
        this.foesBattlefield = battlefield;
    }

    public Battlefield getMyBattlefield() {
        return myBattlefield;
    }

}
