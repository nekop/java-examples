package jp.programmers.jab;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Recorder {

    public static Recorder instance = new Recorder();

    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger errorCount = new AtomicInteger(0);
    List<Long> times = new CopyOnWriteArrayList<Long>();

    void success(long time) {
        successCount.incrementAndGet();
        times.add(time);
    }
    
    void error() {
        errorCount.incrementAndGet();
    }

    void reset() {
        successCount.set(0);
        errorCount.set(0);
        times.clear();
    }

    void report() {
        long total = 0;
        for (Iterator<Long> it = times.iterator(); it.hasNext(); ) {
            total += it.next();
        }
        double average = total / (double)successCount.get();
        System.out.println("successCount=" + successCount);
        System.out.println("errorCount=" + errorCount);
        System.out.println("average=" + average);
    }

}
