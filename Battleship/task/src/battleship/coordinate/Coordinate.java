package battleship.coordinate;

import battleship.constants.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate() { }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordinates(String unformattedCoordinate) {
        setX(unformattedCoordinate);
        setY(unformattedCoordinate);
    }

    public boolean areInvalid() {
        return (x <= 0 || x >= Constants.BATTLEFIELD_ROWS) || (y <= 0 || y >= Constants.BATTLEFIELD_COLUMNS);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(String coordinates) {
        this.x = Integer.parseInt(coordinates.split("(?<=\\D)(?=\\d)")[1]);
    }

    public void setY(String coordinates) {
        this.y = coordinateLetters.getOrDefault(coordinates.charAt(0), 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private static final Map<Character, Integer> coordinateLetters = new HashMap<>() {{
        for (int i = 1; i <= 26; i++) {
            put((char) (i + 64), i);
        }
    }};

}
