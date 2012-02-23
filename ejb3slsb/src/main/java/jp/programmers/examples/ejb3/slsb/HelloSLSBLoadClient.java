package jp.programmers.examples.ejb3.slsb;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloSLSBLoadClient {

    public static void main(final String... args) throws Exception {
        // Confirm single request works before load testing
        HelloSLSBClient.main(args);

        ExecutorService ex = Executors.newCachedThreadPool();
        Runnable r = new Runnable() {
                public void run() {
                    try {
                        HelloSLSBClient.main(args);
                    } catch (Exception ignore) {
                    }
                }
            };
        for (int i = 0; i < 2000; i++) {
            ex.execute(r);
        }
        ex.awaitTermination(60, TimeUnit.SECONDS);
        ex.shutdown();
    }

}
