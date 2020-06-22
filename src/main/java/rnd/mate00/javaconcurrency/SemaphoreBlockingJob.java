package rnd.mate00.javaconcurrency;

import java.util.concurrent.Semaphore;

public class SemaphoreBlockingJob implements Runnable {
    private Semaphore semaphore;
    private String name;

    public SemaphoreBlockingJob(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        criticalOperation();
    }

    private void criticalOperation() {
        System.out.println(name + " A");

        try {
            semaphore.acquire();
            System.out.println(name + " B");
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println(name + " C");
            semaphore.release();
        }
    }
}
