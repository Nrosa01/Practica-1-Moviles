package com.example.gamelogic.levels;

import java.util.Random;

/**
 * Class for generating nonograms.
 */
public class NonogramGenerator {
    public static int[][] GenerateLevel(int rows, int cols) {
        int[][] level = new int[rows][cols];

        // We generate a random grid, each cell has a 50% chance of being filled
        // It usually works
        Random rnd = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                level[row][col] = rnd.nextInt(10) < 5 ? 1: 0;
            }
        }

        // But sometimes it doesn't so...
        // This checks if any row or column is empty or full, if empty add a random cell
        // If full, remove a random cell. It might not be the most efficient solution, but it works.
        boolean isValid = false;
        do
        {
            isValid = true;
            for (int row = 0; row < rows; row++) {
                int sum = 0;
                for (int col = 0; col < cols; col++) {
                    sum += level[row][col];
                }

                if (sum == 0) {
                    level[row][rnd.nextInt(cols)] = 1;
                    isValid = false;
                }
                else if (sum == cols) {
                    level[row][rnd.nextInt(cols)] = 0;
                    isValid = false;
                }
            }

            for (int col = 0; col < cols; col++) {
                int sum = 0;
                for (int row = 0; row < rows; row++) {
                    sum += level[row][col];
                }

                if (sum == 0) {
                    level[rnd.nextInt(rows)][col] = 1;
                    isValid = false;
                }
                else if (sum == rows) {
                    level[rnd.nextInt(rows)][col] = 0;
                    isValid = false;
                }
            }
        } while (!isValid);

        return level;
    }
}
