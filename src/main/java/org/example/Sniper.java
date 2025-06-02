package org.example;

public class Sniper extends Shooter{

    public static final int towerType = 1;


    protected Sniper(Square position) {
        super(2, 100, 20, position, towerType);
    }


}
