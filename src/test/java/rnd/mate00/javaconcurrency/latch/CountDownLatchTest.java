package rnd.mate00.javaconcurrency.latch;

import org.junit.Test;
import rnd.mate00.javaconcurrency.LatchWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountDownLatchTest {

    @Test
    public void waitForThreadResults() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);
        List<String> results = new ArrayList<>();

        List<Callable> workers = Stream
                .generate((Supplier<Callable>) () -> new LatchWorker(latch))
                .limit(4)
                .collect(Collectors.toList());

        for (Callable worker : workers) {
            try {
                results.add((String) worker.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        latch.await();

        System.out.println(results);
    }
}
