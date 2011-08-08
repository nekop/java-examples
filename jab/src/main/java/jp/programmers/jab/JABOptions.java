package jp.programmers.jab;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;
import java.util.List;
import java.util.ArrayList;

public class JABOptions {
    
    @Option(name="-n",usage="Number of requests")
    private int num = 1;

    @Option(name="-c",usage="Concurrency, number of threads")
    private int concurrency = 1;

    @Option(name="-w",usage="Run twice, warm up run and actual benchmark run")
    private boolean warmup = false;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public int getNum() {
        return num;
    }
    
    public void setNum(int num) {
        this.num = num;
    }
    
    public int getConcurrency() {
        return concurrency;
    }
    
    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }
    
    public boolean isWarmup() {
        return warmup;
    }
    
    public void setWarmup(boolean warmup) {
        this.warmup = warmup;
    }

    public List<String> getArguments() {
        return arguments;
    }
    
    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
