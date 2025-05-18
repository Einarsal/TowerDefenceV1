package org.example;

import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {
    private int type;
    public boolean hasGubbe;
    public boolean isRow, isPlacingArea, isStart, isEnd;
    private int width, height;
    public SquareCoord coord;
    public Enemy enemy;

    public Square (int width, int height,int col, int row ){
        this.width = width;
        this.height = height;
        this.coord = new SquareCoord(col, row);
    }

    public void setType(int assignment){
        this.type = assignment;
        setBackground();
    }




    private void setBackground(){
        switch(type){
            case -2,-1 -> setBackground(Color.BLACK);
            case 0 -> setBackground(Color.GREEN);
            case 1 -> setBackground(Color.YELLOW);
        }
    }

    public void paintGubbe(boolean hasGubbe){
        this.hasGubbe = hasGubbe;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(hasGubbe)
            paintEnemy(g, Color.MAGENTA);
        else
            paintEnemy(g, getBackground());

    }

    private void paintEnemy(Graphics g, Color c){
        g.setColor(c);
        g.fillOval(0, 0, width, height);
    }



}
