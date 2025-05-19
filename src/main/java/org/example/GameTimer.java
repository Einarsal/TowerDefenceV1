package org.example;

public class GameTimer implements Runnable {

    public static int fps;
    public final Thread thread;
    private final int sleepTime;
    private final GameContainer mom;
    private int tempcounter = 0;

    public GameTimer(int fps, GameContainer mom) {
        this.fps = fps;
        this.mom = mom;
        thread = new Thread(this);
        sleepTime = calculateSleepTime(fps);
    }

    private int calculateSleepTime(int fps) {
        return 1000 / fps;
    }

    @Override
    public void run() {
        runGame();
    }


    public static void stopGame() {
        GameContainer.gameStopped("you suck");
        System.out.println("gameOver");
    }

    private void runGame() {
        mom.sortPath();
        for (int i = 0; i < 5; i++) {
            GameContainer.enemies.add(new Enemy(GameContainer.path, GameContainer.panel.getFirstPathSquare()));
        }
        mom.spawnEnemies();
        mom.tempTowerSpawner();
        while (true) {
            tick();
            try {
                thread.sleep(sleepTime);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                stopGame();
            }
        }
    }

    private void tick() {
        if(tempcounter < 5) {
        GameContainer.enemies.add(new Enemy(GameContainer.path, GameContainer.panel.getFirstPathSquare()));
        tempcounter++;}
        mom.updateGame();
    }
}
