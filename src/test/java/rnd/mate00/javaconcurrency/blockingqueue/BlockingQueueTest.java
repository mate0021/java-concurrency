package rnd.mate00.javaconcurrency.blockingqueue;

import org.junit.Test;
import rnd.mate00.javaconcurrency.BlockingQueueConsumer;
import rnd.mate00.javaconcurrency.BlockingQueueProducer;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class BlockingQueueTest {

    @Test
    public void sampleUseCase() throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        String poisonMessage = "STOP";

        ExecutorService executor = Executors.newCachedThreadPool();

        IntStream.range(0, 1).forEach(i -> { executor.submit(new Thread(new BlockingQueueConsumer(queue, poisonMessage))); });
        IntStream.range(0, 2).forEach(i -> { executor.submit(new Thread(new BlockingQueueProducer(queue, poisonMessage))); });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
