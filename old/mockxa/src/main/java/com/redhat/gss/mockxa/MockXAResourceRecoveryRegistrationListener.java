package com.redhat.gss.mockxa;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.as.txn.service.TxnServices;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.tm.XAResourceRecoveryRegistry;

public class MockXAResourceRecoveryRegistrationListener
    implements ServletContextListener {

    private static Logger log = Logger.getLogger(MockXAResourceRecoveryRegistrationListener.class);
    private MockXAResourceRecovery mockXAResourceRecovery = new MockXAResourceRecovery();

    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServiceContainer container = CurrentServiceContainer.getServiceContainer();
            ServiceController<XAResourceRecoveryRegistry> service = 
                (ServiceController<XAResourceRecoveryRegistry>)
                container.getService(TxnServices.JBOSS_TXN_ARJUNA_RECOVERY_MANAGER);
            XAResourceRecoveryRegistry registry = service.getValue();
            registry.addXAResourceRecovery(mockXAResourceRecovery);
            log.info("Registered MockXAResourceRecovery");
        } catch (Exception ex) {
            throw new RuntimeException("", ex);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServiceContainer container = CurrentServiceContainer.getServiceContainer();
            ServiceController<XAResourceRecoveryRegistry> service = 
                (ServiceController<XAResourceRecoveryRegistry>)
                container.getService(TxnServices.JBOSS_TXN_ARJUNA_RECOVERY_MANAGER);
            XAResourceRecoveryRegistry registry = service.getValue();
            registry.removeXAResourceRecovery(mockXAResourceRecovery);
        } catch (Exception ex) {
            throw new RuntimeException("", ex);
        }
    }

}
