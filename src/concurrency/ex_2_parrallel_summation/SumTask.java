package concurrency.ex_2_parrallel_summation;

import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000;
    private final int[] array;
    private final int start;
    private final int end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;

        if (length < THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }

        int mid = (start + end) / 2;

        SumTask leftSubTask = new SumTask(array, start, mid);
        SumTask rightSubTask = new SumTask(array, mid, end);

        leftSubTask.fork();

        long rightResult = rightSubTask.compute();
        long leftResult = leftSubTask.join();

        return leftResult + rightResult;
    }
}
