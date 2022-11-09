package com.example.gamelogic.levels;

import java.util.Random;

/**
 * Class for generating nonograms.
 */
public class NonogramGenerator {
    public static int[][] GenerateLevel(int rows, int cols) {
        int[][] level = new int[rows][cols];

        // Grid de celdas con un 50% de posibilidades de spawnear
        Random rnd = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                level[row][col] = rnd.nextInt(10) < 5 ? 1: 0;
            }
        }

        // Comprobar si alguna fila o columna está vacía o rellena. En ese caso quitar o poner una celda aleatoria
        // En dicha fila o columna. No es lo más eficiente pero funciona y la probabilidad de que el bucle sea infinito son nulas
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
