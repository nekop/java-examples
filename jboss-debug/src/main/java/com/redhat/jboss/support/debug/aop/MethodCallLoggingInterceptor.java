package com.redhat.jboss.support.debug.aop;

import org.jboss.logging.Logger;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

public class MethodCallLoggingInterceptor implements Interceptor {

    private static Logger log =
        Logger.getLogger(MethodCallLoggingInterceptor.class);

    public String getName() {
        return "MethodCallLoggingInterceptor";
    }

    public Object invoke(Invocation invocation) throws Throwable {
        if (invocation instanceof MethodInvocation &&
            log.isInfoEnabled()) {
            MethodInvocation mi = (MethodInvocation)invocation;
            String cname = mi.getMethod().getDeclaringClass().getName();
            String mname = mi.getMethod().getName();
            String s = cname + "#" + mname + "()";
            log.info("Start: " + s);
            try {
                return invocation.invokeNext();
            } finally {
                log.info("End: " + s);
            }
        } else {
            return invocation.invokeNext();
        }
    }

}
