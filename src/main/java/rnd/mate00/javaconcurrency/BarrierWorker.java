package rnd.mate00.javaconcurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierWorker implements Runnable { //} Callable<String> {

    private CyclicBarrier barrier;

    public BarrierWorker(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public String call() throws Exception {
        barrier.await(); // <-- await here will cause the worker to suspend
        return Thread.currentThread().getName();
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            barrier.await();
            System.out.println("awaiting complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
