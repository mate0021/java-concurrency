package rnd.mate00.javaconcurrency.latch;

import org.junit.Test;
import rnd.mate00.javaconcurrency.LatchWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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

        results.add("last entry");

        System.out.println(results);
    }

    @Test
    public void latchWithTimeout() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);
        List<Callable> workers = Stream
                .generate(() -> new LatchWorker(latch))
                .limit(4)
                .collect(Collectors.toList());

        List<String> results = new ArrayList<>();
        workers.forEach(w -> {
            try {
                results.add((String) w.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        latch.await(50, TimeUnit.MILLISECONDS); // <-- this will not timeout, but return proper results
                                                        // check BrokenWorker below

        System.out.println(results);
    }

    @Test
    public void latchWithTimeout_BrokenWorker() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);
        List<BrokenLatchWorker> workers = Stream.generate(() -> new BrokenLatchWorker(latch)).limit(4).collect(Collectors.toList());

        List<String> results = new ArrayList<>();
        workers.forEach(w -> {
            try {
                results.add(w.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        latch.await(500, TimeUnit.MILLISECONDS); // <-- this will time out

        System.out.println(results);
    }

    static class BrokenLatchWorker implements Callable<String> {
        private CountDownLatch latch;

        BrokenLatchWorker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public String call() throws Exception {
            throw new RuntimeException("So fatal error occurred in the worker");
        }
    }
}
