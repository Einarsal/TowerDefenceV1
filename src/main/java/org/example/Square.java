package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Square extends JPanel {
//    public boolean isRow, isPlacingArea, isStart, isEnd;
    public Integer pathIndex = -1;
    private int type;
    public boolean hasGubbe, hasTower;
    private final int width, height;
    public SquareCoord coord;
    public Enemy enemy;
    private TowerType towerType;
    private Color gubbeColor;


    private enum TowerType {
        ARCHER,
        SNIPER,
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
        setHasTower(true);
        System.out.println(towerType + " " + this);
        repaint();
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

    public void paintGubbe(boolean hasGubbe, Enemy enemy, Color gubbeColor) {
        this.hasGubbe = hasGubbe;
        this.gubbeColor = gubbeColor;
        repaint();
        if (!hasGubbe) this.enemy = null;
        else this.enemy = enemy;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hasTower)paintTower(g);
        if (hasGubbe) paintEnemy(g, gubbeColor);

    }


    private void paintEnemy(Graphics g, Color c) {
        g.setColor(c);
        g.fillOval(0, 0, width, height);
    }

    private void paintTower(Graphics g ) {
        g.setColor(getTowerColor(towerType));
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
