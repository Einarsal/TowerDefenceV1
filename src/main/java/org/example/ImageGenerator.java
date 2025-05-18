package org.example;

@FunctionalInterface
public interface ImageGenerator {
    Square[][] generateImage( Square[][] squares);

    int[][] squareAssignments = {
            new int[]{0,0,0,0,0,0,0,0,0,0},
            new int[]{-1,1,1,1,1,1,1,0,0,0},
            new int[]{0,0,0,0,0,0,1,0,0,0},
            new int[]{0,0,0,1,1,1,1,0,0,0},
            new int[]{0,0,0,1,0,0,0,0,0,0},
            new int[]{0,0,0,1,0,0,0,0,0,0},
            new int[]{0,0,0,1,1,0,0,0,0,0},
            new int[]{0,0,0,0,1,1,0,0,0,0},
            new int[]{0,0,0,0,0,1,1,1,1,-2},
            new int[]{0,0,0,0,0,0,0,0,0,0}
    };
}
