# HttpSessionSizeFilter

This servlet filter serializes the HttpSession content and logs HttpSession size.

With INFO level it logs only whole HttpSession size (1 log per 1 req). With FINE/DEBUG it logs each attribute size too.

By default it's compiled with Java 6 and targeted to Java EE 6 / Servlet 3.0, but also it can be easily modified to use Java EE 5 / Servlet 2.5 / Java 5.

See (very small) source code for details.

## Install

Add the target/httpsessionsize.jar to WEB-INF/lib directory in your war file.
