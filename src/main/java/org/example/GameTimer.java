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
        runGame();
    }


    public static void stopGame() {
        GameContainer.gameStopped("you suck");
        System.out.println("gameOver");
//        throw new RuntimeException();
    }

    private void runGame() {
        mom.spawnEnemies();
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
        mom.updateGame();
    }
}
