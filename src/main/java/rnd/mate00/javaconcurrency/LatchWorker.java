package rnd.mate00.javaconcurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class LatchWorker implements Callable<String> {

    private CountDownLatch latch;

    public LatchWorker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String call() throws Exception {
        String result = Thread.currentThread().getName();
        latch.countDown();

        return result;
    }
}
