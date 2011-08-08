package jp.programmers.jab;

import static org.kohsuke.args4j.ExampleMode.ALL;
import org.kohsuke.args4j.CmdLineParser;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.util.List;
import java.util.Iterator;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;

public class JAB {

    public static void main(String[] args) throws Exception {
        JABOptions options = new JABOptions();
        CmdLineParser parser = new CmdLineParser(options);
        parser.parseArgument(args);
        URL url = null;
        if (options.getArguments().isEmpty()) {
            System.err.println("Example: JAB" + parser.printExample(ALL));
            System.exit(-1);
        } else {
            url = new URL(options.getArguments().get(0));
        }
        ExecutorService executor =
            createExecutor(options.getConcurrency());
        int num = options.getNum();
        CountDownLatch numLatch = new CountDownLatch(num);
        SimpleURLConnectionTask task =
            new SimpleURLConnectionTask(url);
        task.setLatch(numLatch);
        if (options.isWarmup()) {
            System.out.println("Warming up");
            for (int i = 0; i < num; i++) {
                executor.submit(task);
            }
            numLatch.await(30, TimeUnit.SECONDS);
            reset();
            System.out.println("Done warming up");
        }
        task.setLatch(null);
        System.out.println("Testing");
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.println("Done testing, time=" + (end - start) + "ms");
        report();
    }

    public static AtomicInteger successCount = new AtomicInteger(0);
    public static AtomicInteger errorCount = new AtomicInteger(0);
    public static List<Long> times = new CopyOnWriteArrayList<Long>();
    //public static List<Long> times = 
    //    Collections.synchronizedList(new ArrayList<Long>());

    public static void success(long time) {
        successCount.incrementAndGet();
        times.add(time);
    }
    
    public static void error() {
        errorCount.incrementAndGet();
    }

    public static void reset() {
        successCount.set(0);
        errorCount.set(0);
        times.clear();
    }

    public static void report() {
        long total = 0;
        for (Iterator<Long> it = times.iterator(); it.hasNext(); ) {
            total += it.next();
        }
        double average = total / (double)successCount.get();
        System.out.println("successCount=" + successCount);
        System.out.println("errorCount=" + errorCount);
        System.out.println("average=" + average);
    }

    private static ExecutorService createExecutor(int num) throws Exception {
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

    public static class SimpleURLConnectionTask implements Runnable {
        URL url;
        CountDownLatch latch;
        public SimpleURLConnectionTask(URL url) {
            this.url = url;
        }
        public void setLatch(CountDownLatch latch) {
            this.latch = latch;
        }
        public void run() {
            long start = System.currentTimeMillis();
            try {
                URLConnection conn = url.openConnection();
                BufferedInputStream in =
                    new BufferedInputStream(conn.getInputStream());
                try {
                    if (conn instanceof HttpURLConnection) {
                        int responseCode = ((HttpURLConnection)conn).getResponseCode();
                        if (responseCode < 200 || 299 < responseCode) {
                            error();
                            return;
                        }
                    }
                    while (true) {
                        int r = in.read();
                        if (r == -1) {
                            // EOF
                            break;
                        }
                    }
                } finally {
                    in.close();
                }
            } catch (Throwable t) {
                error();
                return;
            } finally {
                if (latch != null) {
                    latch.countDown();
                }
            }
            long end = System.currentTimeMillis();
            success(end - start);
        }
    }
}
