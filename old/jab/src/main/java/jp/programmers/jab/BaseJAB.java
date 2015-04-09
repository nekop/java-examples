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

public abstract class BaseJAB implements JAB {

    protected JABOptions options;
    
    public void init(JABOptions options) throws Exception {
        this.options = options;
    }

    public void execute() throws Exception {
        if (options.getWarmup() > 0) {
            System.out.println("Warming up");
            fire(options.getWarmup());
            Recorder.instance.reset();
            System.out.println("Done warming up");
        }
        System.out.println("Testing");
        long start = System.currentTimeMillis();
        fire(options.getNum());
        long end = System.currentTimeMillis();
        System.out.println("Done testing, time=" + (end - start) + "ms");
        Recorder.instance.report();
        end();
    }

    protected void end() throws Exception { }

    protected abstract void fire(int num) throws Exception;
}
