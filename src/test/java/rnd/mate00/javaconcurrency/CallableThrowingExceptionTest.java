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
}