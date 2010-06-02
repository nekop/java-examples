package jp.programmers.examples.ejb3.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.ejb.MessageDrivenContext;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.annotation.Resource;

/* Singleton configuration:
@MessageDriven(
    activationConfig={
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="queue/testQueue"),
        @ActivationConfigProperty(propertyName="maxSession", propertyValue="1"),
        @ActivationConfigProperty(propertyName="transactionTimeout", propertyValue="10")
    })
@org.jboss.annotation.ejb.PoolClass(value=org.jboss.ejb3.StrictMaxPool.class, maxSize=1)
*/

@MessageDriven(
    activationConfig={
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="queue/tmpQueue")
    })
public class HelloMDB implements MessageListener {
    @Resource MessageDrivenContext context;
    public void onMessage(Message message) {
        System.out.println("HelloMDB#onMessage(Message)");
        if (message == null) {
           System.out.println("message is null");
            return;
        }
        String s = message.toString();
        String text = null;
        if (message instanceof TextMessage) {
            try {
                text = ((TextMessage)message).getText();
                s += ": text=" + text;
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(s);

        // sleep if long value is passed
        try {
            long sleep = Long.parseLong(text);
            Thread.sleep(sleep);
        } catch (Exception ignore) { }

        // raise exception if requested
        if (text.equalsIgnoreCase("exception")) {
            throw new RuntimeException("Exception requested.");
        }

        // call setRollbackOnly()
        if (text.equalsIgnoreCase("rollback")) {
            context.setRollbackOnly();
        }

    }
}
