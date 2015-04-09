package jp.programmers.jab;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;

public class Executorz {
    static ExecutorService newFixedThreadPoolAndPrefill(int num) throws Exception {
        ExecutorService executor =
            Executors.newFixedThreadPool(num);
        final CountDownLatch latch = new CountDownLatch(1);
        Runnable initTask = new Runnable() {
                public void run() {
                    try {
                        latch.await();
                    } catch (Exception ignore) { }
                }
            };
        for (int i = 0; i < num; i++) {
            executor.submit(initTask);
        }
        latch.countDown();
        return executor;
    }
}
