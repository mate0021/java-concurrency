package rnd.mate00.javaconcurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallingClass {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new JsonCallable(new JsonRetrieverLongTask()));
        executor.shutdown();
    }
}
