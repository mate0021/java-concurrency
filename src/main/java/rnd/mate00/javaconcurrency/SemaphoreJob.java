package rnd.mate00.javaconcurrency;

import java.util.concurrent.Semaphore;

public class SemaphoreJob implements Runnable {

    private Semaphore semaphore;

    private String name;

    public SemaphoreJob(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        criticalOperation();
    }

    private void criticalOperation() {
        System.out.println(name + " A");
        if (semaphore.tryAcquire()) { // <-- don't block if Semaphore is closed
            try {
                System.out.println(name + " B");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(name + " C");
                semaphore.release();
            }
        } else {
            System.out.println(name + " couldn't enter");
        }
    }
}
