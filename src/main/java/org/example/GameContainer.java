package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameContainer {

    public static final String title = "Pe1nab Defence";
    public static final int fps = 5;
    public static Panel panel = new Panel(10, 10);
    static Frame mainFrame;
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Unit> units = new ArrayList<>();
    public static final HashMap<SquareCoord, Square> path = panel.getPath();
    public static ArrayList<Square> sortedPath;


    public GameContainer() throws InterruptedException {
        mainFrame = new Frame(title, panel);
        GameTimer updater = new GameTimer(fps, this);
        updater.thread.start();
        Thread.sleep(5000);
//        updater.thread.interrupt();
    }

    public static void gameStopped(String message) {
        System.exit(0);
//        JOptionPane.showMessageDialog(mainFrame, message);
    }

    public void sortPath() {
        Enemy pathFinder = new Enemy(path, panel.getFirstPathSquare());
        sortedPath = pathFinder.sortPath(path);
    }


    public void updateGame() {
        moveEnemies();
        fireUnits();
    }

    private void moveEnemies() {
        removeDeadEnemies();
        for (Enemy e : enemies) {
            e.move();
            System.out.println(e.health);
        }
    }

    private void removeDeadEnemies() {
        for (Enemy e : enemies) if (e.dead) enemies.remove(e);
    }

    private void fireUnits() {
        for (Unit u : units) {
            u.fire();
        }
    }

    public void spawnEnemies() {
//        Enemy gubbe = new Enemy(panel.getPath(), panel.getFirstPathSquare());
//        enemies.add(gubbe);
    }

    public void tempTowerSpawner() {
//        testShooter = new Archer(panel.grid[4][4]);
        Archer archer = new Archer(panel.grid[5][5]);
        Square s = panel.getSquare(5, 5);
        s.placeTower(0);
        s.setHasTower(true);
        panel.grid[5][5].placeTower(0);
        units.add(archer);
    }


}
