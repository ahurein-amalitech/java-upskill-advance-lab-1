package concurrency.ex_1_fibonacci;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int number = 30;
        try(ForkJoinPool pool = new ForkJoinPool()){
            Fibonacci fibonacciTask = new Fibonacci(number);
            int result = pool.invoke(fibonacciTask);
            System.out.println("Fibonacci number for " + number + " is: " + result);
        }
    }
}