package rnd.mate00.javaconcurrency.semaphore;

import org.junit.Test;
import rnd.mate00.javaconcurrency.SemaphoreJob;

import java.util.stream.IntStream;

public class SemaphoreTest {

    @Test
    public void t() {
        SemaphoreJob job = new SemaphoreJob(3);
        IntStream
                .range(0, 5)
                .forEach(i -> {
                    new Thread(job::criticalOperation).start();
                });
    }
}
