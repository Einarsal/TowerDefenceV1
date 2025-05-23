package org.example;

public class GameTimer implements Runnable {

    public static int fps;
    public final Thread thread;
    private final int sleepTime;
    private final GameContainer mom;

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
        try {
            runGame();
        } catch (InterruptedException e) {
            stopGame();
        }
    }


    public static void stopGame() {
        GameContainer.gameStopped();
        System.out.println("gameOver");
    }

    private void runGame() throws InterruptedException {
//        thread.sleep(1000);
        mom.startWave();
        mom.tempTowerSpawner();
        while (true) {
                thread.sleep(sleepTime);
            tick();
        }
    }

    private void tick() {
        mom.updateGame();
//        if(tempcounter < 3) {
//        GameContainer.enemies.add(GameContainer.panel.getFirstPathSquare()));
//        tempcounter++;}
    }
}
