package rnd.mate00.javaconcurrency.semaphore;

import org.junit.Test;
import rnd.mate00.javaconcurrency.SemaphoreBlockingJob;
import rnd.mate00.javaconcurrency.SemaphoreJob;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreTest {

    @Test
    public void nonBlockingAcquire() {
        Semaphore semaphore = new Semaphore(3);
        SemaphoreJob job = new SemaphoreJob(semaphore);
        IntStream
                .range(0, 5)
                .forEach(i -> {
                    new Thread(job).start();
                });
    }

    @Test
    public void blockingAcquire() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        IntStream.range(0, 3)
                .forEach(i -> executorService.submit(new SemaphoreBlockingJob(semaphore, String.valueOf(i))));

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
