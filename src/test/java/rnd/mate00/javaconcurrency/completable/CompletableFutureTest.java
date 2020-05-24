package rnd.mate00.javaconcurrency.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

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

    @Test
    public void hereWeWaitForFutureComputation_AndDoPostProcessing() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                        return Thread.currentThread().getName();
                    } catch (InterruptedException e) {
                        return "";
                    }
                })
                .thenApply(String::toUpperCase); // <-- we're returning Future<String>, with some post processing result

        System.out.println(future.get());
    }

    @Test
    public void waitForFutureComputation_AndAcceptIt() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> Thread.currentThread().getName())
                .thenAccept(s -> System.out.println("our computation returned: " + s)); // <-- here we're just taking result of
                                                                                        // computations but don't return anything

        System.out.println(future.get()); // <-- null here
    }

    @Test
    public void waitForFutureComputation_AndThenRunSomethingElse() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> Thread.currentThread().getName())
                .thenAccept(s -> System.out.println("First was run on: " + s))
                .thenRun(() -> System.out.println("Last is run on " + Thread.currentThread().getName()));

        System.out.println(future.get());
    }

    @Test
    public void composingTwoCompletables() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete("1st stage");
        });

        CompletableFuture<String> finalFuture = future.thenCompose(str -> CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return str + " 2nd stage";
        }));

        System.out.println(future.get());
        System.out.println(finalFuture.get());
    }

    @Test
    public void shorterVersionOfAbove() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1st stage";
        }).thenCompose(resultFromPreviousSupplier -> CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return resultFromPreviousSupplier + " 2nd stage";
        }));

        System.out.println(future.get());
    }
}
