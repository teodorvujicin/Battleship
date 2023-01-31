package battleship.battlefield;

import battleship.constants.Constants;
import battleship.coordinate.Coordinate;
import battleship.ship.Ship;

import java.util.*;

public class Battlefield {
    public final String[][] battlefield;
    public final static int ROWS    = Constants.BATTLEFIELD_ROWS;
    public final static int COLUMNS = Constants.BATTLEFIELD_COLUMNS;
    private final Set<Coordinate> forbiddenCoordinates;
    private final Set<Coordinate> shipCoordinates;
    private final List<Ship> ships;

    public Battlefield() {
        this.battlefield = new String[ROWS][COLUMNS];
        this.forbiddenCoordinates = new HashSet<>();
        this.shipCoordinates = new HashSet<>();
        this.ships = new ArrayList<>();

        createBattlefield(this.battlefield);
    }

    public boolean hitShip(Coordinate coordinate) {
        return this.isCoordinateTaken(coordinate);
    }

    public void removeHitShipCoordinate(Coordinate coordinate) {
        for (Ship ship : this.ships) {
            if (ship.isOnCoordinate(coordinate)) {
                ship.removeCoordinate(coordinate);
                this.shipCoordinates.remove(coordinate);

                if (!this.shipCoordinates.isEmpty()) {
                    if (ship.isSunk()) {
                        System.out.println("You sank a ship!");
                    } else {
                        System.out.println("You hit a ship!");
                    }
                    break;
                }
            }
        }
    }

    public boolean hasShips() {
        return !this.shipCoordinates.isEmpty();
    }

    public boolean positionShip(Ship ship, Coordinate startCoordinates, Coordinate endCoordinates) {
        if (this.forbiddenCoordinates.contains(startCoordinates) || this.forbiddenCoordinates.contains(endCoordinates)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }

        if (startCoordinates.getX() > endCoordinates.getX() || startCoordinates.getY() > endCoordinates.getY()) {
            ship.setStartCoordinates(endCoordinates);
            ship.setEndCoordinates(startCoordinates);
        } else {
            ship.setStartCoordinates(startCoordinates);
            ship.setEndCoordinates(endCoordinates);
        }

        ship.setOrientation();

        if (ship.coordinatesMatchSize()) {
            if (ship.isVertical()) {
                for (int i = ship.getStartCoordinates().getX(); i <= ship.getEndCoordinates().getX(); i++) {
                    this.markCoordinateAsTaken(i, ship.getStartCoordinates().getY());
                    ship.addCoordinate(new Coordinate(i, ship.getStartCoordinates().getY()));
                    this.shipCoordinates.add(new Coordinate(i, ship.getStartCoordinates().getY()));
                    addForbiddenCoordinates(new Coordinate(i, ship.getStartCoordinates().getY()));
                }
            } else if (ship.isHorizontal()) {
                for (int i = ship.getStartCoordinates().getY(); i <= ship.getEndCoordinates().getY(); i++) {
                    this.markCoordinateAsTaken(ship.getStartCoordinates().getX(), i);
                    ship.addCoordinate(new Coordinate(ship.getStartCoordinates().getX(), i));
                    this.shipCoordinates.add(new Coordinate(ship.getStartCoordinates().getX(), i));
                    addForbiddenCoordinates(new Coordinate(ship.getEndCoordinates().getX(), i));
                }
            }
            this.ships.add(ship);
            return true;

        } else if (!ship.isVertical() && !ship.isHorizontal()) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;

        } else {
            System.out.println("Error! Wrong length of the " + ship.getShipClass() + "! Try again:");
            return false;
        }
    }

    private void addForbiddenCoordinates(Coordinate coordinate) {
        this.forbiddenCoordinates.add(coordinate);
        this.forbiddenCoordinates.add(new Coordinate(coordinate.getX(), coordinate.getY() + 1));
        this.forbiddenCoordinates.add(new Coordinate(coordinate.getX(), coordinate.getY() - 1));
        this.forbiddenCoordinates.add(new Coordinate(coordinate.getX() + 1, coordinate.getY()));
        this.forbiddenCoordinates.add(new Coordinate(coordinate.getX() - 1, coordinate.getY()));
    }

    private boolean isCoordinateTaken(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        return this.battlefield[y][x].equals(Constants.SHIP);
    }

    private void markCoordinateAsTaken(int x, int y) {
        this.battlefield[y][x] = Constants.SHIP;
    }

    public void registerHit(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        this.battlefield[y][x] = Constants.HIT;
    }

    public void registerMiss(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        this.battlefield[y][x] = Constants.MISS;
    }

    public void printToConsole() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(this.battlefield[i][j] + Constants.EMPTY_SPACE);
            }
            System.out.println();
        }
    }

    private static void createBattlefield(String[][] battlefield) {
        battlefield[0][0] = Constants.EMPTY_SPACE;

        createColumns(battlefield);
        createRows(battlefield);
        createTheSea(battlefield);
    }

    private static void createColumns(String[][] battlefield) {
        // Populate the first row with numbers
        for (int i = 1; i < COLUMNS; i++) {
            battlefield[0][i] = String.valueOf(i);
        }
    }

    private static void createRows(String[][] battlefield) {
        // Populate the first row with letters
        for (int i = 1; i < ROWS; i++) {
            battlefield[i][0] = Character.toString((char) (i + 64));
        }
    }

    private static void createTheSea(String[][] battlefield) {
        // Populate the rest of the table with "~"
        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLUMNS; j++) {
                battlefield[i][j] = Constants.SEA;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Battlefield that = (Battlefield) o;
        return Arrays.deepEquals(battlefield, that.battlefield) && Objects.equals(forbiddenCoordinates, that.forbiddenCoordinates) && Objects.equals(shipCoordinates, that.shipCoordinates) && Objects.equals(ships, that.ships);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ROWS, COLUMNS, forbiddenCoordinates, shipCoordinates, ships);
        result = 31 * result + Arrays.deepHashCode(battlefield);
        return result;
    }

}
