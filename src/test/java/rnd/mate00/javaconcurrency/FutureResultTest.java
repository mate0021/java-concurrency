package rnd.mate00.javaconcurrency;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

public class FutureResultTest {

    @Test
    public void futureTask() throws InterruptedException, ExecutionException {
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            int i = 0;
            while (i < 1000) {
                System.out.println("calculating...");
                Thread.sleep(100);
                i += 100;
            }
            return "result from future task";
        });

        Executors.newSingleThreadExecutor().submit(stringFutureTask);

        System.out.println(stringFutureTask.get());
    }

    @Test
    public void waitUntilDone() throws InterruptedException, ExecutionException {
        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
            Thread.sleep(2000);
            return "result from callable";
        });

        while (!future.isDone()) {
            System.out.println("waiting for completion");
            Thread.sleep(200);
        }

        System.out.println(future.get());
    }

    @Test(expected = TimeoutException.class)
    public void timeout() throws InterruptedException, ExecutionException, TimeoutException {
        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
            Thread.sleep(2000);
            return "long waited result";
        });

        String result = future.get(200, TimeUnit.MILLISECONDS);
    }

    @Test
    public void cancellingTask() {
        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
            Thread.sleep(2000);
            return "Let's cancel this";
        });

        future.cancel(true);

        assertTrue(future.isCancelled());
    }

    @Test(expected = CancellationException.class)
    public void getFromCancelledTask() throws ExecutionException, InterruptedException {
        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
            Thread.sleep(2000);
            return "Let's cancel it";
        });

        future.cancel(true);

        future.get();
    }
}
