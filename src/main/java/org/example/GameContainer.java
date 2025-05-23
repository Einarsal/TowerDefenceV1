package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class GameContainer {

    public static final String title = "Pe1nab Defence";
    public static final int fps = 2;
    public static Panel panel = new Panel(10, 10);
    static Frame mainFrame;
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Unit> units = new ArrayList<>();
    public static final HashMap<SquareCoord, Square> path = panel.getPath();
    public static ArrayList<Square> sortedPath;


    public GameContainer() throws InterruptedException {
        mainFrame = new Frame(title, panel);
        sortPath();
        GameTimer updater = new GameTimer(fps, this);
        updater.thread.start();
        Thread.sleep(5000);
//        updater.thread.interrupt();
    }

    public static void gameStopped() {
        System.out.println("Game stopped");
    }

    public void sortPath() {
        Enemy pathFinder = new Enemy(panel.getFirstPathSquare());
        sortedPath = pathFinder.sortPath(path);

        for (int i = 0; i < sortedPath.size(); i++) {
            Square s = sortedPath.get(i);
            int col = s.coord.col;
            int row = s.coord.row;
            panel.grid[col][row].setPathIndex(i);
        }
    }


    public void updateGame() {
        fireUnits();
        moveEnemies();
    }

    private void moveEnemies() {
        removeDeadEnemies();
        if (enemies.isEmpty()) finnishWave();
        for (Enemy e : enemies) {
            e.move();
        }
    }
    public void finnishWave(){
        gameStopped();
    }

    private void removeDeadEnemies() {
        enemies.removeIf(e -> e.dead);
    }

    private void fireUnits() {
        for (Unit u : units) {
            u.fire();
        }
    }

    public void startWave(){
        spawnEnemies(new Enemy[]{new Enemy(panel.getFirstPathSquare())});
    }

    public void spawnEnemies(Enemy[] newEnemies) {

        enemies.add(new Enemy(panel.getFirstPathSquare()));
    }

    public void tempTowerSpawner() {
        Archer archer = new Archer(panel.grid[5][5]);
        panel.placeUnit(archer);
        units.add(archer);
    }


}
