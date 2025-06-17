package org.example;

public class Archer extends Shooter /*implements Unit*/{

    public static final int towerType = 0;

    public Archer(int range, int speed, int damage, Square position) {
        super(position, towerType);
    }

    public Archer(Square position) {
        super(position, towerType);
    }

    


//    @Override
//    public void fire() {
//        dealDamage(damage);
//    }
}
