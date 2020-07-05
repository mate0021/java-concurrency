package rnd.mate00.javaconcurrency.blockingqueue;

import org.junit.Test;
import rnd.mate00.javaconcurrency.BlockingQueueConsumer;
import rnd.mate00.javaconcurrency.BlockingQueueProducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

public class BlockingQueueTest {

    @Test
    public void sampleUseCase() throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        String poisonMessage = "STOP";

        IntStream.range(0, 20).forEach(i -> { new Thread(new BlockingQueueConsumer(queue, poisonMessage)).start(); });
        IntStream.range(0, 5).forEach(i -> { new Thread(new BlockingQueueProducer(queue, poisonMessage)).start(); });

        Thread.sleep(2000);
    }
}
