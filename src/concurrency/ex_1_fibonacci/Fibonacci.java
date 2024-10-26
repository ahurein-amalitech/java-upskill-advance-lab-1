package concurrency.ex_1_fibonacci;

import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {
    private final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n <= 1){
            return n;
        }

        Fibonacci f1 = new Fibonacci(n-1);
        Fibonacci f2 = new Fibonacci(n-2);

        f1.fork();

        int resultF2 = f2.compute();
        int resultF1 = f1.join();

        return resultF1 + resultF2;
    }
}
