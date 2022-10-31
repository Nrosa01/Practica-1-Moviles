package com.example.gamelogic.levels;

import java.util.Random;

public class NonogramGenerator {
    public static int[][] GenerateLevel(int rows, int cols) {
        int[][] level = new int[rows][cols];

        Random rnd = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                level[row][col] = rnd.nextInt(10) < 5 ? 1: 0;
            }
        }

        return level;
    }
}
