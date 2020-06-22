package rnd.mate00.javaconcurrency;

import java.util.concurrent.Semaphore;

public class SemaphoreJob implements Runnable {

    private Semaphore semaphore;

    public SemaphoreJob(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        criticalOperation();
    }

    private void criticalOperation() {
        System.out.println("performing some calculations");
        if (semaphore.tryAcquire()) {
            try {
                System.out.println(String.format("critical section done by %s / available slots %d", Thread.currentThread(), semaphore.availablePermits()));
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(String.format("%s releasing semaphore", Thread.currentThread()));
                semaphore.release();
            }
        }
    }
}
