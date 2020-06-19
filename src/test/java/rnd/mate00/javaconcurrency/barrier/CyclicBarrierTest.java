package rnd.mate00.javaconcurrency.barrier;

import org.junit.Test;
import rnd.mate00.javaconcurrency.BarrierWorker;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    @Test
    public void sampleBarrier() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("Computation done");
        });

        BarrierWorker w1 = new BarrierWorker(barrier);
        BarrierWorker w2 = new BarrierWorker(barrier);
        BarrierWorker w3 = new BarrierWorker(barrier);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(w1).get();
        executorService.submit(w2).get();
        executorService.submit(w3).get();
    }
}
