package org.example;

import java.util.HashMap;
import java.util.Map;

public class Enemy {

    public int pathIndex = 0;

    private Square currentSquare;
    Map<SquareCoord, Square> path;
//    private boolean isDone;
    private Direction lastDirection;
    Direction[] directions = Direction.values();
    Map<Direction, Direction> oppositeDirection = new HashMap<>();
    SquareCoord[] cords;

    public Enemy(Map<SquareCoord, Square> path, Square firstSquare) {
        this.path = path;
        cords = addCoordsToArray();
        oppositeDirection = createOppositeDirection();
        lastDirection = Direction.RIGHT;
        currentSquare = firstSquare;
        spawn(currentSquare);
    }

    private SquareCoord[] addCoordsToArray() {
        SquareCoord[] list = new SquareCoord[path.size()];
        int i = 0;
        for (Square s : path.values()) {
            list[i] = s.coord;
            i++;
        }
        return list;
    }

    private Map<Direction, Direction> createOppositeDirection() {
        Map<Direction, Direction> map = new HashMap<>();
        map.put(Direction.RIGHT, Direction.LEFT);
        map.put(Direction.LEFT, Direction.RIGHT);
        map.put(Direction.DOWN, Direction.UP);
        map.put(Direction.UP, Direction.DOWN);
        return map;
    }

    public void move() {
        if (pathIndex + 1 == path.size()) return;
        currentSquare.paintGubbe(false);
        currentSquare = getNextPath();
        currentSquare.paintGubbe(true);
        pathIndex++;
    }

    private Square getNextPath() {
        Square nextPath = null;
        for (Direction d : directions) {
            if (d == oppositeDirection.get(lastDirection)) {
                continue;
            }
            switch (d) {
                case RIGHT -> nextPath = getNextPathFromMap(false, 1);
                case LEFT -> nextPath = getNextPathFromMap(false, -1);
                case DOWN -> nextPath = getNextPathFromMap(true, 1);
                case UP -> nextPath = getNextPathFromMap(true, -1);
            }
            if (nextPath != null) {
                lastDirection = d;
                System.out.println(d);
                break;
            }
        }
        return nextPath;
    }


    private Square getNextPathFromMap(boolean vertical, int increase) {
        SquareCoord currentCoord = currentSquare.coord;
        SquareCoord newSquareCoord;
        int col = currentCoord.col;
        int row = currentCoord.row;
        if (vertical) newSquareCoord = getCordsFromArray(row + increase, col );
        else newSquareCoord = getCordsFromArray(row, col + increase );
        Square newSquare;
        try {
            newSquare = path.get(newSquareCoord);
        } catch (NullPointerException e) {
            return null;
        }
        return newSquare;
    }

    private SquareCoord getCordsFromArray(int row, int col) {
        for (SquareCoord sc : cords) {
            if (sc.row == row && sc.col == col) return sc;
        }
        return null;
    }

    private enum Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN


    }

    public void spawn(Square currentSquare) {
        currentSquare.paintGubbe(true);
    }

    public void takeDamage(int damage){

    }
}
