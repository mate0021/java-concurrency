package rnd.mate00.javaconcurrency.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class ObtainFutureAndWait {

    @Test
    public void obtainFutureAndWaitForCompletion() throws ExecutionException, InterruptedException {
        Computation c = new Computation();
        int result = c.doIntensive().get(); // <-- we'll block here until future.complete() is called

        assertEquals(42, result);
    }
}

class Computation {
    CompletableFuture<Integer> doIntensive() {
        CompletableFuture<Integer> result = new CompletableFuture<>();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result.complete(42);
        return result;
    }
}
