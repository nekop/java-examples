package jp.programmers.examples.ejb3.slsb;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

public class HelloSLSBLoadClient {

    public static final String DEFAULT_PROVIDER_URL = "localhost:1099";

    public static void main(String... args) throws Exception {
        String providerUrl = DEFAULT_PROVIDER_URL;
        if (args.length != 0) {
            providerUrl = args[0];
        }

        final String param = providerUrl;
        ExecutorService ex = Executors.newCachedThreadPool();
        Runnable r = new Runnable() {
                public void run() {
                    try {
                        HelloSLSBClient.main(param);
                    } catch (Exception ignore) {
                    }
                }
            };
        for (int i = 0; i < 10; i++) {
            ex.execute(r);
        }
        ex.shutdown();
    }

}
