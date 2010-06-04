package org.jboss.injbossaop;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

public class ValueExpressionMetrics implements Interceptor {

    public String getName() {
        return "ValueExpressionMetrics";
    }

    public Object invoke(Invocation invocation) throws Throwable {
        System.out.println("*****ExpressionMetrics:invoke");
        long startTime = System.currentTimeMillis();
        try {
            return invocation.invokeNext();
        } finally {
            long endTime = System.currentTimeMillis() - startTime;
            java.lang.reflect.Method m = ((MethodInvocation) invocation).getMethod();
            System.out.println("method " + m.toString() + " time: " + endTime + "ms");
        }

    }

}
