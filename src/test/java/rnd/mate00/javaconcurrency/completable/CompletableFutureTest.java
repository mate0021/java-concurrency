package rnd.mate00.javaconcurrency.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class CompletableFutureTest {

    @Test
    public void simpleCase() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete("result of computations");
        });

        System.out.println(future.get());
    }

    @Test
    public void noNeedToWait() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completed = CompletableFuture.completedFuture("we know the result already");
        System.out.println(completed.get());
    }

    @Test
    public void useRunAsyncForJustRunInSeparateThread() {
        CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
        });
    }

    @Test
    public void useSupplierToProvideResult() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> Thread.currentThread().getName());

        System.out.println(future.get());
    }
}
