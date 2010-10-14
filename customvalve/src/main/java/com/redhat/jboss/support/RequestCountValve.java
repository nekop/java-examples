package com.redhat.jboss.support;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.util.concurrent.atomic.AtomicInteger;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

/**
 * A valve which has current request count.
 */
public class RequestCountValve extends ValveBase {
    
    private AtomicInteger count = new AtomicInteger(0);
    
    public void invoke(Request request, Response response)
        throws IOException, ServletException {
        try {
            count.incrementAndGet();
            getNext().invoke(request, response);
        } finally {
            count.decrementAndGet();
        }
    }

    public AtomicInteger getCount() {
        return count;
    }
    
}
