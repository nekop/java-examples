package jp.programmers.examples.ejb2.mdb;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @ejb.bean name="HelloMDB"
 * @jboss.destination-jndi-name name="queue/tmpQueue" 
 */
public class HelloMDB implements MessageDrivenBean, MessageListener {

    private MessageDrivenContext context = null;

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

    public void ejbCreate() { }
    public void ejbRemove() { }
    public void setMessageDrivenContext(MessageDrivenContext context) {
        this.context = context;
    }
}



























































