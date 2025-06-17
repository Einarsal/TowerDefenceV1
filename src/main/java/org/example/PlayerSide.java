package org.example;

public class PlayerSide {
    public static int health, money;

    public PlayerSide(int health, int money) {
        PlayerSide.health = health;
        PlayerSide.money = money;
    }

    public static void takeDamage(int damage) {
        health -= damage;
        System.out.println("Player Health: " + health);
    }
}
