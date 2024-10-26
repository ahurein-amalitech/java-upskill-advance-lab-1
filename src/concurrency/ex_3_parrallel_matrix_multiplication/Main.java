package concurrency.ex_3_parrallel_matrix_multiplication;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int size = 1024;

        int[][] A = new int[size][size];
        int[][] B = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                A[i][j] = i + j;
                B[i][j] = i - j;
            }
        }

        ForkJoinPool pool = new ForkJoinPool();

        MatrixMultiplicationTask task = new MatrixMultiplicationTask(A, 0, 0, B, 0, 0, size);

        int[][] result = pool.invoke(task);

        System.out.println("Result matrix top-left corner:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}
