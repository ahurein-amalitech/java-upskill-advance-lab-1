package concurrency.ex_2_parrallel_summation;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        try(ForkJoinPool pool = new ForkJoinPool()){
            SumTask task = new SumTask(array, 0, array.length);
            long result = pool.invoke(task);
            System.out.println("Array sum: " + result);
        }
    }
}