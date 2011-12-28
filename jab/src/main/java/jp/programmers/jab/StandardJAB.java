package jp.programmers.jab;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.util.List;
import java.util.Iterator;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;

public class StandardJAB extends BaseJAB {

    ExecutorService executor;
    SimpleURLConnectionTask task;
    CountDownLatch numLatch;
    
    public void init(JABOptions options) throws Exception {
        super.init(options);
        executor = Executorz.newFixedThreadPoolAndPrefill(options.getConcurrency());
        URL url = new URL(options.getArguments().get(0));
        task = new SimpleURLConnectionTask(url);
    }

    protected void fire(int num) throws Exception {
        // There is no API on ExecutorService to know all tasks are finished, so we use Latch
        numLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            executor.submit(task);
        }
        numLatch.await(300, TimeUnit.SECONDS);
    }

    protected void end() throws Exception {
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

    class SimpleURLConnectionTask implements Runnable {
        URL url;
        public SimpleURLConnectionTask(URL url) {
            this.url = url;
        }
        public void run() {
            long start = System.currentTimeMillis();
            byte[] buff = new byte[8192];
            try {
                URLConnection conn = url.openConnection();
                BufferedInputStream in =
                    new BufferedInputStream(conn.getInputStream());
                try {
                    if (conn instanceof HttpURLConnection) {
                        int statusCode = ((HttpURLConnection)conn).getResponseCode();
                        if (statusCode < 200 || 299 < statusCode) {
                            Recorder.instance.error();
                            return;
                        }
                    }
                    while (true) {
                        int r = in.read(buff);
                        if (r < 1 && in.read() == -1) {
                            // EOF
                            break;
                        }
                    }
                } finally {
                    in.close();
                }
            } catch (Throwable t) {
                t.printStackTrace();
                Recorder.instance.error();
                return;
            } finally {
                long end = System.currentTimeMillis();
                Recorder.instance.success(end - start);
                if (numLatch != null) {
                    numLatch.countDown();
                }
            }
        }
    }
}
