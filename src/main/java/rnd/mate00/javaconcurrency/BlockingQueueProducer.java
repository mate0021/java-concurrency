package rnd.mate00.javaconcurrency;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueProducer implements Runnable {

    private BlockingQueue<String> queue;

    private String poisonMessage;

    public BlockingQueueProducer(BlockingQueue<String> queue, String poisonMessage) {
        this.queue = queue;
        this.poisonMessage = poisonMessage;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                queue.put(UUID.randomUUID().toString());
                Thread.sleep(200);
            }
            queue.put(poisonMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
