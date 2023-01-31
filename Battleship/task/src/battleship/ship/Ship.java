package battleship.ship;

import battleship.coordinate.Coordinate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Ship {
    private final String shipClass;
    private final int size;
    private Coordinate startCoordinates;
    private Coordinate endCoordinates;
    private final Set<Coordinate> coordinates;
    private ShipOrientation orientation;

    public Ship(String shipClass, int size) {
        this.shipClass = shipClass;
        this.size = size;
        this.coordinates = new HashSet<>();
    }

    public boolean isOnCoordinate(Coordinate coordinate) {
        return this.coordinates.contains(coordinate);
    }

    public void addCoordinate(Coordinate coordinate) {
        this.coordinates.add(coordinate);
    }

    public void removeCoordinate(Coordinate coordinate) {
        this.coordinates.remove(coordinate);
    }

    public boolean isSunk() {
        return this.coordinates.isEmpty();
    }

    public void setOrientation() {
        if (startCoordinates.getY() == endCoordinates.getY()) {
            this.orientation = ShipOrientation.VERTICAL;
        } else if (startCoordinates.getX() == endCoordinates.getX()) {
            this.orientation = ShipOrientation.HORIZONTAL;
        }
    }

    public boolean isVertical() {
        return this.orientation == ShipOrientation.VERTICAL;
    }

    public boolean isHorizontal() {
        return this.orientation == ShipOrientation.HORIZONTAL;
    }

    public boolean coordinatesMatchSize() {
        if (this.isVertical()) {
            return Math.abs(endCoordinates.getX() - startCoordinates.getX()) == this.size - 1;
        } else if (this.isHorizontal()) {
            return Math.abs(endCoordinates.getY() - startCoordinates.getY()) == this.size - 1;
        } else {
            return false;
        }
    }

    public String getShipClass() {
        return this.shipClass;
    }

    public int getSize() {
        return this.size;
    }

    public Coordinate getStartCoordinates() {
        return this.startCoordinates;
    }

    public void setStartCoordinates(Coordinate startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public Coordinate getEndCoordinates() {
        return this.endCoordinates;
    }

    public void setEndCoordinates(Coordinate endCoordinates) {
        this.endCoordinates = endCoordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return size == ship.size && Objects.equals(shipClass, ship.shipClass) && Objects.equals(startCoordinates, ship.startCoordinates) && Objects.equals(endCoordinates, ship.endCoordinates) && Objects.equals(coordinates, ship.coordinates) && orientation == ship.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipClass, size, startCoordinates, endCoordinates, coordinates, orientation);
    }
}
