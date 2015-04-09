package jp.programmers.examples;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ContentLengthFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException { }
    
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        ContentLengthResponseWrapper responseWrapper =
            new ContentLengthResponseWrapper((HttpServletResponse)response);
        chain.doFilter(request, responseWrapper);
        responseWrapper.flushResponse();
    }

    public void destroy() { }
}

