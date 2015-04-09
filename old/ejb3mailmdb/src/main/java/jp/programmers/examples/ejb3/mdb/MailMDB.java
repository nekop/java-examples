package jp.programmers.examples.ejb3.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.mail.Message;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.resource.adapter.mail.inflow.MailListener;

@MessageDriven(activationConfig={
   @ActivationConfigProperty(propertyName="mailServer", propertyValue="server"),
   @ActivationConfigProperty(propertyName="mailFolder", propertyValue="INBOX"),
   @ActivationConfigProperty(propertyName="storeProtocol", propertyValue="imap"),
   @ActivationConfigProperty(propertyName="userName", propertyValue="foo"),
   @ActivationConfigProperty(propertyName="password", propertyValue="foo")
})
@ResourceAdapter("mail-ra.rar")
public class MailMDB implements MailListener {
    public void onMessage(Message mail) {
        try {
            System.out.println("New email: " + mail.getSubject());
        } catch (javax.mail.MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
