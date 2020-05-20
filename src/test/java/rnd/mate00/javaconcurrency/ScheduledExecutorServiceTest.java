package rnd.mate00.javaconcurrency;

import org.junit.Test;

import java.util.concurrent.*;

public class ScheduledExecutorServiceTest {

    private Callable<String> simpleTask = () -> Thread.currentThread().getName() + " simple task";

    private Runnable simpleAction = () -> {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " simple runnable");
    };

    @Test
    public void scheduleTask_ExecuteAfterSomeTime() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<String> future = scheduledExecutor.schedule(simpleTask, 3, TimeUnit.SECONDS);

        System.out.println(future.get());
    }

    @Test
    public void scheduleTask_ExecuteAtEachInterval() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        // this doesn't wait until one simpleAction is done before scheduling another one
        ScheduledFuture<?> future = scheduledExecutor.scheduleAtFixedRate(simpleAction, 1000, 200, TimeUnit.MILLISECONDS);

        try {
            System.out.println(future.get(6, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            // that's ok...
        }
    }

    @Test
    public void scheduleTask() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        // this will wait until one scheduleAction is done, waits for fixed delay and then start another
        ScheduledFuture<?> future = scheduledExecutor.scheduleWithFixedDelay(simpleAction, 1000, 200, TimeUnit.MILLISECONDS);

        try {
            System.out.println(future.get(6, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            // that's ok...
        }
    }
}
