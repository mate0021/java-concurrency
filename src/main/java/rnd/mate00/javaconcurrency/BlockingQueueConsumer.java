package rnd.mate00.javaconcurrency;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueConsumer implements Runnable {

    private BlockingQueue<String> queue;

    private String poisonMessage;

    public BlockingQueueConsumer(BlockingQueue<String> queue, String poisonMessage) {
        this.queue = queue;
        this.poisonMessage = poisonMessage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = queue.take();
                if (poisonMessage.equals(msg)) {
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " " + msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
