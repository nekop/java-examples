package jp.programmers.examples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

public class Redirect {

    /**
     * Build an absolute URL for redirect
     */
    public static String toRedirectURL(HttpServletRequest request, HttpServletResponse response, String url) {
        StringBuilder sb = new StringBuilder();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        sb.append(scheme);
        sb.append("://");
        sb.append(serverName);
        if ((scheme.equals("http") && serverPort != 80) ||
            (scheme.equals("https") && serverPort != 443)) {
            sb.append(":");
            sb.append(serverPort);
        }
        if (url.startsWith("/")) {
            sb.append(url);
        } else {
            String requestURI = request.getRequestURI();
            int lastSlash = requestURI.lastIndexOf("/");
            if (lastSlash != -1) {
                sb.append(requestURI.substring(0, lastSlash));
                sb.append("/");
                sb.append(response.encodeRedirectURL(url));
            }
        }
        return sb.toString();
    }
}
