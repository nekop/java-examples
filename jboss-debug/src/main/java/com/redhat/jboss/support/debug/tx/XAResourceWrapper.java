package com.redhat.jboss.support.debug.tx;

import java.io.Serializable;
import javax.transaction.xa.Xid;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.XAException;
import org.jboss.logging.Logger;

/*
 * NOTE: This XAResource wrapper class is currently not used from other classes.
 */
public class XAResourceWrapper implements XAResource {

    private static Logger log = Logger.getLogger(XAResourceWrapper.class);
    private XAResource res;

    public XAResourceWrapper(XAResource res) {
        this.res = res;
    }

    public void start(Xid xid, int flags) throws XAException {
        log.info("start('" + xid + "', " + flags + ')');
        res.start(xid, flags);
    }
    
    public void end(Xid xid, int flags) throws XAException {
        log.info("end('" + xid + "', " + flags + ')');
        res.end(xid, flags);
    }

    public int prepare(Xid xid) throws XAException {
        log.info("prepare('" + xid + "')");
        return res.prepare(xid);
    }

    public void commit(Xid xid, boolean onePhase) throws XAException {
        log.info("commit('" + xid + "', " + onePhase + ')');
        res.commit(xid, onePhase);
    }

    public void rollback(Xid xid) throws XAException {
        log.info("rollback('" + xid + "')");
        res.rollback(xid);
    }

    public void forget(Xid xid) throws XAException {
        log.info("forget('" + xid + "')");
        res.forget(xid);
    }

    public Xid[] recover(int flags) throws XAException {
        log.info("recover()");
        return res.recover(flags);
    }

    public boolean isSameRM(XAResource xaResource) throws XAException {
        if (xaResource instanceof XAResourceWrapper) {
            xaResource = ((XAResourceWrapper)xaResource).res;
        }
        return res.isSameRM(xaResource);
    }

    public int getTransactionTimeout() throws XAException {
        return res.getTransactionTimeout();
    }

    public boolean setTransactionTimeout(int transactionTimeout) throws XAException {
        return res.setTransactionTimeout(transactionTimeout);
    }

}
