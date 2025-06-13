package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class GameContainer {

    public static final String TITLE = "Pe1nab Defence";
    public static final String CONFIG_URL = "src/main/resources/config.xml";
    public static int FPS;
    public static Panel panel = new Panel(10, 10);
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Unit> units = new ArrayList<>();
    public static final HashMap<SquareCoord, Square> PATH = panel.getPath();
    public static ArrayList<Square> sortedPath;


    public GameContainer() throws InterruptedException {
        new Frame(TITLE, panel);
        FPS = getFPS();
        sortPath();
        GameTimer updater = new GameTimer(FPS, this);
        updater.thread.start();
        Thread.sleep(5000);
//        updater.thread.interrupt();
    }

    private int getFPS() {
        String fps = ConfigParser.getProperty(CONFIG_URL, "fps").getFirst().trim();
        System.out.println(fps);
        return Integer.parseInt(fps);
    }



    public static void gameStopped() {
        System.out.println("Game stopped");
    }

    public void sortPath() {
        Enemy pathFinder = new Enemy(panel.getFirstPathSquare(), 1);
        sortedPath = pathFinder.sortPath(PATH);

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

    public void finnishWave() {
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

    public void startWave() {
        spawnEnemies(new Enemy[]{new Enemy(panel.getFirstPathSquare(),1)});
    }

    public void spawnEnemies(Enemy[] newEnemies) {

        enemies.add(new Enemy(panel.getFirstPathSquare(),1));
    }

    public void tempTowerSpawner() {
        Archer archer = new Archer(panel.grid[5][5]);
        panel.placeUnit(archer);
        units.add(archer);
    }


}
