<%@page import="javax.naming.InitialContext" %>
<%@page import="javax.transaction.Transaction" %>
<%@page import="javax.transaction.TransactionManager" %>
<%@page import="javax.transaction.xa.XAException" %>
<%@page import="com.redhat.gss.mockxa.MockXAResource" %>

<%
    try {
        TransactionManager tm = InitialContext.doLookup("java:/TransactionManager");
        // Clear existing tx
        try { tm.rollback(); } catch (Exception ignore) { }

        tm.begin();
        Transaction t = tm.getTransaction();
        MockXAResource res1 = new MockXAResource();
        res1.resourceId = 1;
        MockXAResource res2 = new MockXAResource();
        res2.resourceId = 2;

        //res2.crashInCommit = true;

        res2.exceptionInCommit = true;
        res2.exceptionErrorCode = XAException.XAER_RMERR;
        t.enlistResource(res1);
        t.enlistResource(res2);
        tm.commit();
    } catch (Exception ex) {
        throw new RuntimeException("txtest", ex);
    }

%>
