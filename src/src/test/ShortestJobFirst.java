package test;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
public class ShortestJobFirst {

    private ExecutorService threadExecutor;
    private PriorityBlockingQueue<ClientRunnable> priorityBlockingQueue;

    public static Comparator<ClientRunnable> Comparator = java.util.Comparator.comparingInt(ClientRunnable::getBoardSize);

    public ShortestJobFirst(Integer poolSize, Integer queueSize) {
        threadExecutor = Executors.newFixedThreadPool(poolSize);
        priorityBlockingQueue = new PriorityBlockingQueue<>(queueSize, Comparator);
        ExecutorService threadScheduler = Executors.newSingleThreadExecutor();
        threadScheduler.execute(() -> {
            while (true) {
                try {
                    if (!priorityBlockingQueue.isEmpty()) {
                        threadExecutor.execute(priorityBlockingQueue.take());
                    }
                } catch (InterruptedException exception) {
                    System.out.println("ShortestJobFirst.ShortestJobFirst()"+ exception.toString());
                    break;
                }
            }
        });
    }

    public PriorityBlockingQueue<ClientRunnable> getPriorityQueue() {
        return priorityBlockingQueue;
    }

    public void close() {
        threadExecutor.shutdown();
    }
}