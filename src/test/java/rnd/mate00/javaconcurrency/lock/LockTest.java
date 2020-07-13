package rnd.mate00.javaconcurrency.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    @Test
    public void useThisIdion() {
        Lock lock = new ReentrantLock();

        lock.lock();
        try {
            System.out.println(lock);
        } finally {
            System.out.println("Whatever happens, unlock it.");
            lock.unlock();
        }
    }
}
