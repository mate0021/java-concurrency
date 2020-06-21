package rnd.mate00.javaconcurrency;

import java.util.concurrent.Semaphore;

public class SemaphoreJob {

    private Semaphore semaphore;

    public SemaphoreJob(int slots) {
        semaphore = new Semaphore(slots);
    }

    public void criticalOperation() {
        System.out.println("performing some calculations");
        if (semaphore.tryAcquire()) {
            try {
                System.out.println(String.format("critical section done by %s / available slots %d", Thread.currentThread(), semaphore.availablePermits()));
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }
}
