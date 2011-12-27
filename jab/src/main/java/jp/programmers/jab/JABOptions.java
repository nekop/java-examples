package jp.programmers.jab;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;
import java.util.List;
import java.util.ArrayList;

public class JABOptions {
    
    @Option(name="-n",usage="Number of requests")
    private int num = 1;

    @Option(name="-w",usage="Number of requests in warm up phase")
    private int warmup = 0;

    @Option(name="-c",usage="Concurrency")
    private int concurrency = 1;

    @Option(name="-t",usage="Number of threads, used only in NIO JAB")
    private int threads = 1;

    @Option(name="-j",usage="JAB Type")
    private String type = "";

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public int getNum() {
        return num;
    }
    
    public void setNum(int num) {
        this.num = num;
    }
    
    public int getWarmup() {
        return warmup;
    }
    
    public void setWarmup(int warmup) {
        this.warmup = warmup;
    }

    public int getConcurrency() {
        return concurrency;
    }
    
    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }
    
    public int getThreads() {
        return threads;
    }
    
    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public List<String> getArguments() {
        return arguments;
    }
    
    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
