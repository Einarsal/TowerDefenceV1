package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Square extends JPanel {
    public Integer pathIndex = -1;
    private int type;
    public boolean hasGubbe, hasTower;
    public boolean isRow, isPlacingArea, isStart, isEnd;
    private int width, height;
    public SquareCoord coord;
    public Enemy enemy;
    public TowerType towerType;


    private enum TowerType {
        ARCHER,
    }

    public Square(int width, int height, int col, int row) {
        this.width = width;
        this.height = height;
        this.coord = new SquareCoord(col, row);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public void setHasTower(boolean hasTower) {
        this.hasTower = hasTower;
    }

    public void placeTower(int typeIndex) {
        this.hasTower = true;
        this.towerType = TowerType.values()[typeIndex];
        System.out.println(towerType + " " + this);
    }

    public void setPathIndex(int i) {
        this.pathIndex = i;
    }

    public void setType(int assignment) {
        this.type = assignment;
        setBackground();
    }

    private void setBackground() {
        switch (type) {
            case -2, -1 -> setBackground(Color.BLACK);
            case 0 -> setBackground(Color.GREEN);
            case 1 -> setBackground(Color.YELLOW);
            case 2 -> {
                placeTower(0);
                setBackground(Color.RED);
            }
        }
    }

    public void paintGubbe(boolean hasGubbe, Enemy enemy) {
        this.hasGubbe = hasGubbe;
        repaint();
        if (!hasGubbe) this.enemy = null;
        else this.enemy = enemy;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (hasTower){
            System.out.println("hasTower");
            g.setColor(Color.black);
            g.fillOval(0, 0, width, height);
        }

        if (hasGubbe) paintEnemy(g, Color.MAGENTA);
//        System.out.println(hasTower);

    }

    private void paintEnemy(Graphics g, Color c) {
        g.setColor(c);
        g.fillOval(0, 0, width, height);
    }

    private void paintTower(Graphics g, TowerType type) {
//        g.setColor(getTowerColor(type));
        g.setColor(Color.black);
        g.fillOval(0, 0, width, height);
    }

    private Color getTowerColor(TowerType type) {
        switch (type) {
            case ARCHER -> {
             return Color.BLACK;
            }
            case null, default -> {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "(" + coord.col + "," + coord.row + ")";
    }
}
