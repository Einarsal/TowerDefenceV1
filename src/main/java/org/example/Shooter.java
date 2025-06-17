package org.example;

import java.util.ArrayList;

//public class Shooter {
public class Shooter implements Unit {

    protected int range, speed, damage;
    public final int towerType;
    private int frameCount = 0;

    public Shooter(Square position, int towerType) {
        this.position = position;
        this.towerType = towerType;
        setStats();
    }

    private void setStats() {
        String towerName = "";
        switch (towerType) {
            case 0 -> towerName = "Archer";
            case 1 -> towerName = "Sniper";
        }

        ArrayList<String> towerStats = ConfigParser.getProperty(GameContainer.CONFIG_URL, towerName);
        this.range = Integer.parseInt(towerStats.getFirst().trim());
        this.speed = GameContainer.FPS/(Integer.parseInt(towerStats.get(1).trim()));
        this.damage = Integer.parseInt(towerStats.get(2).trim());
    }

    Square position;


    protected void dealDamage(int damage) {
        AreaScanner areaScanner = (range, positionSquare) -> {
            ArrayList<Square> squaresInRange = findSquaresInRange(range, positionSquare);
            ArrayList<Enemy> enemies = new ArrayList<>();
            for (Square s : squaresInRange) {
                if (s.hasGubbe) enemies.add(s.enemy);
            }
            return sortEnemies(enemies);
//            return enemies;
        };
        ArrayList<Enemy> enemiesInRange = areaScanner.scanArea(range, position);
        if (enemiesInRange.isEmpty()) return;

        enemiesInRange.get(0).takeDamage(damage);

    }

    private ArrayList<Enemy> sortEnemies(ArrayList<Enemy> enemies) {
        if (enemies.size() < 2) return enemies;
        ArrayList<Enemy> sortedEnemies = new ArrayList<>(enemies);
        for (int i = sortedEnemies.size(); i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (sortedEnemies.get(j).getIndex() < sortedEnemies.get(j + 1).getIndex()) {
                    Enemy temp = sortedEnemies.get(j);
                    sortedEnemies.set(j, sortedEnemies.get(j + 1));
                    sortedEnemies.set(j + 1, temp);
                }
            }
        }
        return sortedEnemies;
    }


    private ArrayList<Square> findSquaresInRange(int range, Square position) {
        ArrayList<Square> squaresInRange = new ArrayList<>();
        int row = position.coord.row;
        int col = position.coord.col;
        for (Square s : GameContainer.PATH.values()) {
            if (isInRange(range, s, row, col)) squaresInRange.add(s);
        }
        return squaresInRange;
    }

    protected boolean isInRange(int range, Square square, int row, int col) {
        int sRow = square.coord.row;
        int sCol = square.coord.col;
        return sRow <= row + range && sRow >= row - range && sCol <= col + range && sCol >= col - range;

    }

    private void tick() {
        if (frameCount++ % speed == 0) dealDamage(damage);
    }

    @Override
    public void fire() {
        tick();
//        dealDamage(damage);
    }

    @Override
    public int getRow() {
        return position.coord.row;
    }

    @Override
    public int getCol() {
        return position.coord.col;
    }

    @Override
    public int getType() {
        return towerType;
    }
}
