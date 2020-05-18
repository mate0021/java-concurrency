package rnd.mate00.javaconcurrency;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    @Test
    public void exampleOfForkJoinTask() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();

        FactorialSquareCalculatorTask task = new FactorialSquareCalculatorTask(10);

        ForkJoinTask<Integer> submittedTask = pool.submit(task);
        System.out.println(submittedTask.get());
    }
}

class FactorialSquareCalculatorTask extends RecursiveTask<Integer> {

    private int n;

    FactorialSquareCalculatorTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            return n;
        }

        FactorialSquareCalculatorTask task = new FactorialSquareCalculatorTask(n - 1);

        task.fork();

        return n * n + task.join();
    }
}