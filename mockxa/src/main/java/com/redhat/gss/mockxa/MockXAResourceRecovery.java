package com.redhat.gss.mockxa;

import org.jboss.tm.XAResourceRecovery;
import javax.transaction.xa.XAResource;
import org.jboss.logging.Logger;

public class MockXAResourceRecovery implements XAResourceRecovery {

    private static Logger log = Logger.getLogger(MockXAResourceRecovery.class);

    public MockXAResourceRecovery() {
        log.info("MockXAResourceRecovery");
    }

    public XAResource[] getXAResources() {
        log.info("getXAResources()");
        return new XAResource[] { new MockXAResource() };
    }

}
