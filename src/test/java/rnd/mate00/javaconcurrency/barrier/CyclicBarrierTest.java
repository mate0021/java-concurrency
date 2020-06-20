package rnd.mate00.javaconcurrency.barrier;

import org.junit.Test;
import rnd.mate00.javaconcurrency.BarrierWorker;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    @Test
    public void sampleBarrier() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("Computation done");
        });

        BarrierWorker w1 = new BarrierWorker(barrier);
        BarrierWorker w2 = new BarrierWorker(barrier);
        BarrierWorker w3 = new BarrierWorker(barrier);

        new Thread(w1).start();
        new Thread(w2).start();
        new Thread(w3).start();
    }
}
