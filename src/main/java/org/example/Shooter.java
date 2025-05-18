package org.example;

import java.util.ArrayList;

public class Shooter implements Unit {

    protected int range, speed, damage;

    public Shooter(int range, int speed, int damage, Square position) {
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.position = position;
    }

    Square position;

    public void dealDamage(int damage) {
        AreaScanner areaScanner = (range, positionSquare) -> {
            ArrayList<Square> squaresInRange = findSquaresInRange(range, positionSquare);
            ArrayList<Enemy> enemies = new ArrayList<>();
            for(Square s : squaresInRange){
                if(s.hasGubbe) enemies.add(s.enemy);
            }
            return enemies;
        };
        ArrayList<Enemy> enemiesInRange = areaScanner.scanArea(range, position);

    }

    private ArrayList<Square> findSquaresInRange(int range, Square position){
        ArrayList<Square> squaresInRange = new ArrayList<>();
        int row = position.coord.row;
        int col = position.coord.col;

        for(Square s : GameContainer.path.values()){
            if(isInRange(range, s, row, col)) squaresInRange.add(s);
        }
        return squaresInRange;
    }

    public boolean isInRange(int range, Square square, int row, int col){
        int sRow = square.coord.row;
        int sCol = square.coord.col;
        return sRow <= row+range && sRow >= row-range && sCol <= col+range && sCol >= col-range;

    }


    @Override
    public void fire() {
        dealDamage(damage);
    }
}
