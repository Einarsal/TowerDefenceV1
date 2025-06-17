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
    public static int wave = 0;
    public static int tick = 0;
    private int spawnInterval = 0, enemiesToSpawn = 0, spawnEnemyType = 0, lastSpawnTick = 0;
    private int enemyType = 0;
    private ArrayList<Integer> waveAmounts = new ArrayList<>();

    public GameContainer() throws InterruptedException {
        new Frame(TITLE, panel);
        FPS = getFPS();
        sortPath();
        GameTimer updater = new GameTimer(FPS, this);
        updater.thread.start();
//        Thread.sleep(10000);
//        updater.thread.interrupt();
    }

    private int getFPS() {
        ArrayList<String> amounts = ConfigParser.getProperty(CONFIG_URL, "wave0");
        for (String s : amounts) {
            System.out.println(s);
        }

        String fps = ConfigParser.getProperty(CONFIG_URL, "fps").getFirst().trim();
        System.out.println(fps);
        return Integer.parseInt(fps);
    }


    public static void gameStopped() {
//        System.out.println("Game stopped");
        try {
            Thread.sleep(2000);
//            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sortPath() {
        Enemy pathFinder = new Enemy(panel.getFirstPathSquare(), -1);
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


        if (enemiesToSpawn > 0 && lastSpawnTick <= tick - spawnInterval) {
            spawnEnemy(spawnEnemyType);
            enemiesToSpawn--;
            lastSpawnTick = tick;
            if (enemiesToSpawn == 0) {
                enemyType++;
                if (enemyType < waveAmounts.size()) {
                    setWaveValues();
                }
            }
        }
        tick++;
    }

    private void setWaveValues() {
        enemiesToSpawn = waveAmounts.get(enemyType) - 1;
        spawnEnemyType = enemyType;
        spawnInterval = (int)(1.5*FPS / Enemy.getSpeed(enemyType));
        lastSpawnTick = tick;
        if(enemiesToSpawn < 0) return;
        spawnEnemy(enemyType);
    }

    private void moveEnemies() {
        removeDeadEnemies();
        if (enemies.isEmpty()) finnishWave();
        for (Enemy e : enemies) {
            e.move();
        }
    }

    public void finnishWave() {
        wave++;
        startWave();
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
        String waveName = "wave" + wave;

        ArrayList<String> waveAmounts = ConfigParser.getProperty(CONFIG_URL, waveName);
        for (String s : waveAmounts) {
            this.waveAmounts.add(Integer.parseInt(s.trim()));
        }
        setWaveValues();

    }

    public void spawnEnemy(int enemyType) {

        enemies.add(new Enemy(panel.getFirstPathSquare(), enemyType));
    }

    public void tempTowerSpawner() {
        Archer archer = new Archer(panel.grid[5][5]);
        panel.placeUnit(archer);
        units.add(archer);
    }


}
