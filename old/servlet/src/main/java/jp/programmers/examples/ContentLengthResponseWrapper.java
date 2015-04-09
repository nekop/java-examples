package jp.programmers.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ContentLengthResponseWrapper extends HttpServletResponseWrapper {

    private HttpServletResponse response;

    private boolean isOutputStream = false;
    private ServletOutputStream sout;
    private ByteArrayOutputStream bout;

    private boolean isWriter = false;
    private StringWriter sw;
    private PrintWriter pw;

    public ContentLengthResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
        bout = new ByteArrayOutputStream();
        sout = new ServletOutputStream() {
                public void write(int b) throws IOException {
                    bout.write(b);
                }
            };

        sw = new StringWriter();
        pw = new PrintWriter(sw);
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (isWriter) {
            // Invalid call sequence, propagate it to throw exception
            response.getWriter();
            response.getOutputStream();
        }
        isOutputStream = true;
        return sout;
    }

    public PrintWriter getWriter() throws IOException {
        if (isOutputStream) {
            // Invalid call sequence, propagate it to throw exception
            response.getOutputStream();
            response.getWriter();
        }
        isWriter = true;
        return pw;
    }

    public void flushResponse() throws IOException {
        if (isOutputStream) {
            try {
                sout.flush();
            } catch (IOException ignore) { }
            byte[] result = bout.toByteArray();
            int length = result.length;
            response.setContentLength(length);
            response.getOutputStream().write(result);
        } else if (isWriter) {
            pw.flush();
            String s = sw.toString();
            String charset = response.getCharacterEncoding();
            if (charset == null) {
                charset = "ISO-8859-1";
            }
            int length = s.getBytes(charset).length;
            response.setContentLength(length);
            response.getWriter().write(s);
        }
    }
}

