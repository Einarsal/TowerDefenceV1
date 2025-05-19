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
    private Shooter testShooter;


    public GameContainer() throws InterruptedException {
        mainFrame = new Frame(title, panel);
        GameTimer updater = new GameTimer(fps, this);
        updater.thread.start();
        Thread.sleep(10000);
        updater.thread.interrupt();
    }

    public static void gameStopped(String message) {
        System.exit(0);
//        JOptionPane.showMessageDialog(mainFrame, message);
    }

    public void sortPath(){
        Enemy pathFinder = new Enemy(path, panel.getFirstPathSquare());
        sortedPath = pathFinder.sortPath(path);
    }


    public void updateGame() {
        moveEnemies();
        fireUnits();
    }

    private void moveEnemies() {
        for (Enemy e : enemies) {
            e.move();
        }
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

    public void tempTowerSpawner(){
//        testShooter = new Archer(panel.grid[4][4]);
        Archer archer = new Archer(panel.grid[4][4]);
        units.add(archer);
    }


}
