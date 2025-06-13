package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Enemy {

    public boolean dead = false;
    public int pathIndex = 0;
    public Square currentSquare;
    private HashMap<SquareCoord, Square> path;
    private Direction lastDirection;
    private final Direction[] directions = Direction.values();
    private HashMap<Direction, Direction> oppositeDirection = new HashMap<>();
    private SquareCoord[] cords;
    public int health;
    private int speed;
    private int frameCounter = 0;

    public Enemy(Square firstSquare, int enemyType) {
//        this.speed = framerate / speed;
        init(firstSquare, enemyType);
    }

    private void setEnemyStats(int enemyType){
        String tag;
        switch(enemyType){
            case 1 -> tag = "Normal";
            default -> tag = "";
        }

        ArrayList<String> stats = ConfigParser.getProperty(GameContainer.CONFIG_URL, tag);
        setHealth(Integer.parseInt(stats.get(0).trim()));
        this.speed = GameContainer.FPS/Integer.parseInt(stats.get(1).trim());


    }

//    public Enemy

    private void init(Square firstSquare, int enemyType) {
        this.path = GameContainer.PATH;
        cords = addCoordsToArray();
        oppositeDirection = createOppositeDirection();
        lastDirection = Direction.RIGHT;
        currentSquare = firstSquare;
        setEnemyStats(enemyType);
//        setHealth(100);
        spawn(currentSquare);
    }


    public void setHealth(int health) {
        this.health = health;
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

    public ArrayList<Square> sortPath(HashMap<SquareCoord, Square> path) {
        die();
        ArrayList<Square> sortedPath = new ArrayList<>();
        for (int i = 0; i + 1 < path.size(); i++) {
            Square nextPath = getNextPath();
            nextPath.setPathIndex(i);
            sortedPath.add(nextPath);
        }

        return sortedPath;
    }

    private HashMap<Direction, Direction> createOppositeDirection() {
        HashMap<Direction, Direction> map = new HashMap<>();
        map.put(Direction.RIGHT, Direction.LEFT);
        map.put(Direction.LEFT, Direction.RIGHT);
        map.put(Direction.DOWN, Direction.UP);
        map.put(Direction.UP, Direction.DOWN);
        return map;
    }

    public void move() {
        if (frameCounter++ % speed != 0) return;
        if (pathIndex + 1 >= path.size()) return;
        currentSquare.paintGubbe(false, this);
        currentSquare = getNextPath();
        currentSquare.paintGubbe(true, this);
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
                break;
            }
        }
        return nextPath;
    }

    public int getIndex() {
        return pathIndex;
    }

    private Square getNextPathFromMap(boolean vertical, int increase) {
        SquareCoord currentCoord = currentSquare.coord;
        SquareCoord newSquareCoord;
        int col = currentCoord.col;
        int row = currentCoord.row;
        if (vertical) newSquareCoord = getCordsFromArray(row + increase, col);
        else newSquareCoord = getCordsFromArray(row, col + increase);
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
        currentSquare.paintGubbe(true, this);
    }

    public String toString() {
        return currentSquare.toString();
    }

    private void die() {
        currentSquare.paintGubbe(false, this);
        GameContainer.enemies.remove(this);
        dead = true;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) die();
        System.out.println(" Taking " + damage + " damage " + health);
    }
}


