package rnd.mate00.javaconcurrency.lock;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    @Test
    public void useThisIdiom() {
        Lock lock = new ReentrantLock();

        lock.lock();
        try {
            System.out.println(lock);
        } finally {
            System.out.println("Whatever happens, unlock it.");
            lock.unlock();
        }
    }

    @Test
    public void nonSynchronizedSharedObject() throws InterruptedException {
        SharedObject shared = new SharedObject();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println(Thread.currentThread() + " " + shared.incrementAndGet());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } );
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

    }
}

class SharedObject {
    private int counter;
    private Lock lock = new ReentrantLock();

    int incrementAndGet() throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(10);
            counter++;
        } finally {
            lock.unlock();
        }
        return counter;
    }
}