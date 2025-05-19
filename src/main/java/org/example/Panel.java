package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Panel extends JPanel {

    private final int rows, cols;
    public final Square[][] grid;
    private HashMap<SquareCoord, Square> path;
    private ArrayList<Square> pathSquares;
    private Square firstPathSquare = null;

    public Panel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        Dimension size = new Dimension(600, 600);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        pathSquares = new ArrayList<>();
        grid = createSquares(rows, cols, 60, 60);
        addSquares(grid);
    }

    private void addSquares(Square[][] grid) {
        setLayout(new GridLayout(rows, cols));
        for (Square[] row : grid) {
            for (Square s : row) {
                add(s);
            }
        }
    }

    private Square[][] createSquares(int rows, int cols, int width, int height) {
        Square[][] grid = new Square[rows][];
        for (int i = 0; i < rows; i++) {
            Square[] row = new Square[cols];
            for (int j = 0; j < cols; j++) {
                row[j] = new Square(width, height, j, i);
            }
            grid[i] = row;
        }
        return generateImage(grid, _ -> {
            if (!imageIsValid(rows, cols, ImageGenerator.squareAssignments)) GameTimer.stopGame();
            path = new HashMap<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {

                    if (ImageGenerator.squareAssignments[i][j] == 1) {
                        if(firstPathSquare == null) {
                            System.out.println(i + " " + j);
                            firstPathSquare = grid[i][j];
                        }
                        path.put(grid[i][j].coord, grid[i][j]);
                    }
                    grid[i][j].setType(ImageGenerator.squareAssignments[i][j]);
                }
            }
            return grid;
        });
    }



    private boolean imageIsValid(int rows, int cols, int[][] assignments) {
        if (assignments.length != rows) return false;
        for (int[] row : assignments) {
            if (row.length != cols) return false;
        }
        return true;
    }

    private Square[][] generateImage(Square[][] grid, ImageGenerator ig) {
        return ig.generateImage(grid);
    }

    public HashMap<SquareCoord, Square> getPath() {
        return path;
    }

    public Square getFirstPathSquare() {
        return firstPathSquare;
    }
}
