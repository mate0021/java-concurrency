package rnd.mate00.javaconcurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class BarrierWorker implements Callable<String> {

    private CyclicBarrier barrier;

    public BarrierWorker(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public String call() throws Exception {
        barrier.await();
        return Thread.currentThread().getName();
    }
}
