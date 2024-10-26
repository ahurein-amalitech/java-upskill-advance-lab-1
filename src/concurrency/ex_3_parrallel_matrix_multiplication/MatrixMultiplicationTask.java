package concurrency.ex_3_parrallel_matrix_multiplication;

import java.util.concurrent.RecursiveTask;

public class MatrixMultiplicationTask extends RecursiveTask<int[][]> {
    private static final int THRESHOLD = 64; // Threshold for direct computation
    private final int[][] A, B;
    private final int rowA, colA, rowB, colB, size;

    public MatrixMultiplicationTask(int[][] A, int rowA, int colA, int[][] B, int rowB, int colB, int size) {
        this.A = A;
        this.B = B;
        this.rowA = rowA;
        this.colA = colA;
        this.rowB = rowB;
        this.colB = colB;
        this.size = size;
    }

    @Override
    protected int[][] compute() {
        int[][] result = new int[size][size];

        if (size <= THRESHOLD) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        result[i][j] += A[rowA + i][colA + k] * B[rowB + k][colB + j];
                    }
                }
            }
            return result;
        }

        int newSize = size / 2;

        MatrixMultiplicationTask task1Part1 = new MatrixMultiplicationTask(A, rowA, colA, B, rowB, colB, newSize);
        MatrixMultiplicationTask task1Part2 = new MatrixMultiplicationTask(A, rowA, colA + newSize, B, rowB + newSize, colB, newSize);

        MatrixMultiplicationTask task2Part1 = new MatrixMultiplicationTask(A, rowA, colA, B, rowB, colB + newSize, newSize);
        MatrixMultiplicationTask task2Part2 = new MatrixMultiplicationTask(A, rowA, colA + newSize, B, rowB + newSize, colB + newSize, newSize);

        MatrixMultiplicationTask task3Part1 = new MatrixMultiplicationTask(A, rowA + newSize, colA, B, rowB, colB, newSize);
        MatrixMultiplicationTask task3Part2 = new MatrixMultiplicationTask(A, rowA + newSize, colA + newSize, B, rowB + newSize, colB, newSize);

        MatrixMultiplicationTask task4Part1 = new MatrixMultiplicationTask(A, rowA + newSize, colA, B, rowB, colB + newSize, newSize);
        MatrixMultiplicationTask task4Part2 = new MatrixMultiplicationTask(A, rowA + newSize, colA + newSize, B, rowB + newSize, colB + newSize, newSize);

        task1Part1.fork();
        task1Part2.fork();
        task2Part1.fork();
        task2Part2.fork();
        task3Part1.fork();
        task3Part2.fork();
        task4Part1.fork();
        task4Part2.fork();

        int[][] C11 = addMatrices(task1Part1.join(), task1Part2.join());
        int[][] C12 = addMatrices(task2Part1.join(), task2Part2.join());
        int[][] C21 = addMatrices(task3Part1.join(), task3Part2.join());
        int[][] C22 = addMatrices(task4Part1.join(), task4Part2.join());

        combineQuadrants(result, C11, C12, C21, C22, newSize);

        return result;
    }

    private int[][] addMatrices(int[][] a, int[][] b) {
        int size = a.length;
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private void combineQuadrants(int[][] result, int[][] C11, int[][] C12, int[][] C21, int[][] C22, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = C11[i][j];
                result[i][j + size] = C12[i][j];
                result[i + size][j] = C21[i][j];
                result[i + size][j + size] = C22[i][j];
            }
        }
    }
}
