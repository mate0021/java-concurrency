package rnd.mate00.javaconcurrency.completable;

import org.junit.Test;
import rnd.mate00.javaconcurrency.SupplierThrowingException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompletableFutureHandlingErrors {

    private SupplierThrowingException faultySupplier = new SupplierThrowingException(1);

    @Test
    public void nothingThrown_UnlessCallGetOrJoin() {
        CompletableFuture.supplyAsync(faultySupplier);
    }

    @Test(expected = ExecutionException.class)
    public void notHandlingExceptions() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(faultySupplier).get();
    }

    @Test
    public void handlingExceptions() throws ExecutionException, InterruptedException {
        String result = CompletableFuture.supplyAsync(faultySupplier)
                .handle((input, throwable) -> {
                    if (input != null) {
                        return input;
                    } else {
                        return "default";
                    }
                })
                .get();

        assertEquals("default", result);
    }

    @Test
    public void useExceptionally() throws ExecutionException, InterruptedException {
        String result = CompletableFuture.supplyAsync(faultySupplier)
                .exceptionally(ex -> "Completed with exception: " + ex)
                .get();

        System.out.println(result);
        assertTrue(result.startsWith("Completed with exception"));
    }

    @Test
    public void supplyExceptionalResultToNextStage() throws ExecutionException, InterruptedException {
        String result = CompletableFuture.supplyAsync(faultySupplier)
                .exceptionally(ex -> "default")
                .thenApply(String::toUpperCase)
                .get();

        assertEquals("DEFAULT", result);
    }

    @Test
    public void manyStagesOfComputation() throws ExecutionException, InterruptedException {
        int wrongArgument = 0;
        int result = CompletableFuture.supplyAsync(() -> 20 / wrongArgument)
                .thenApply(input -> input + 5)
                .thenApply(input -> input * 2)
                .exceptionally(ex -> 1)
                .thenApply(input -> input + 1)
                .get();

        assertEquals(2, result);
    }
}
