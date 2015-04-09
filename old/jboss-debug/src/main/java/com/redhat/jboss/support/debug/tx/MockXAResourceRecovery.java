package com.redhat.jboss.support.debug.tx;

import com.arjuna.ats.jta.recovery.XAResourceRecovery;
import javax.transaction.xa.XAResource;
import org.jboss.logging.Logger;

public class MockXAResourceRecovery implements XAResourceRecovery {

    private static Logger log = Logger.getLogger(MockXAResourceRecovery.class);
    private boolean hasMoreResources = false;

    public MockXAResourceRecovery() {
        log.info("MockXAResourceRecovery");
    }

    public XAResource getXAResource() {
        log.info("getXAResource()");
        return new MockXAResource();
    }

    public boolean initialise(String p) {
        log.info("initialise(" + p + ")");
        return true;
    }

    public boolean hasMoreResources() {
        hasMoreResources = !hasMoreResources;
        return hasMoreResources;
    }
 
}
