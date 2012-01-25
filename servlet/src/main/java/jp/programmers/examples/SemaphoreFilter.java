package jp.programmers.examples;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class SemaphoreFilter implements Filter {
    
    public static final String CONCURRENCY = "CONCURRENCY";
    public static final String FAIR = "FAIR";
    public static final String BLOCK_TIMEOUT_MILLIS = "BLOCK_TIMEOUT_MILLIS";

    protected FilterConfig config;
    protected Semaphore semaphore;
    protected int concurrency = 10;
    protected boolean fair;
    protected long blockTimeoutMillis = 0;
    
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        if (config.getInitParameter(CONCURRENCY) != null) {
            try {
                concurrency = Integer.parseInt(config.getInitParameter(CONCURRENCY));
            } catch (NumberFormatException ex) {
                // ignore, todo logging
            }
        }
        if (config.getInitParameter(FAIR) != null) {
            fair = "true".equalsIgnoreCase(config.getInitParameter(FAIR));
        }
        if (config.getInitParameter(BLOCK_TIMEOUT_MILLIS) != null) {
            try {
                blockTimeoutMillis = Long.parseLong(config.getInitParameter(BLOCK_TIMEOUT_MILLIS));
            } catch (NumberFormatException ex) {
                // ignore, todo logging
            }
        }
        this.semaphore = new Semaphore(concurrency, fair);
    }
    
    public void destroy() { }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        if (controlConcurrency(request, response)) {
            boolean shouldRelease = true;
            try {
                if (!semaphore.tryAcquire(blockTimeoutMillis, TimeUnit.MILLISECONDS)) {
                    shouldRelease = false;
                    permitDenied(request, response);
                    return;
                }
                chain.doFilter(request, response);
            } catch (InterruptedException e) {
                shouldRelease = false;
                permitDenied(request, response);
                return;
            } finally {
                if (shouldRelease) {
                    semaphore.release();
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }
    
    /**
     * Subclass friendly method to add conditions.
     */
    protected boolean controlConcurrency(ServletRequest request,
                                         ServletResponse response)
        throws IOException, ServletException {
        return true;
    }

    /**
     * Subclass friendly method to add error handling when a permit isn't granted.
     */
    protected void permitDenied(ServletRequest request,
                                ServletResponse response)
        throws IOException, ServletException {
        // We are busy, send 503 Service Temporary Unavailable
        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse)response).sendError(503);
        } else {
            // todo, what should we do?
        }
    }

}
