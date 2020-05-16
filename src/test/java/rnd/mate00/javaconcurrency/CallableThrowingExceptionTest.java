package rnd.mate00.javaconcurrency;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.*;

public class CallableThrowingExceptionTest {

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    @Test
    public void futureIsDone_ButNoExceptionIsThrown() throws InterruptedException {
        Future<String> future = executor.submit(new CallableThrowingException());

        Thread.sleep(50);

        assertTrue(future.isDone());
    }

    @Test(expected = ExecutionException.class) // <-- callable throws some RuntimeExc, but it is wrapped by ExecutionException
    public void futureIsDone_ExceptionIsThrownOnGet() throws ExecutionException, InterruptedException {
        Future<String> future = executor.submit(new CallableThrowingException());

        Thread.sleep(50);
        assertTrue(future.isDone());

        future.get();
    }

    @Test
    public void getWrappedException() throws InterruptedException {
        Future<String> future = executor.submit(new CallableThrowingException());

        Thread.sleep(50);

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            System.out.println(cause.getMessage());
        }
    }

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

}