package org.example;

public class Archer extends Shooter implements Unit{
    public Archer(int range, int speed, int damage, Square position) {
        super(range, speed, damage, position);
    }

    public Archer(Square position) {
        super(2, 100, 20, position);
    }



    @Override
    public void fire() {
        dealDamage(damage);
    }
}
