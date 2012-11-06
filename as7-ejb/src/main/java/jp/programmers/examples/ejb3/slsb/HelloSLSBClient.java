package jp.programmers.examples.ejb3.slsb;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import jp.programmers.examples.ejb3.slsb.Hello;

public class HelloSLSBClient {

    public static final String EJBJAR_NAME = "example-as7-ejb";
    public static final String EJB_NAME = "HelloSLSB";

    public static void main(String... args) throws Exception {
        String ejbName = EJB_NAME;
        if (args.length != 0) {
            ejbName = args[0];
        }
        String jndiName = "ejb:/" + EJBJAR_NAME + "/" + ejbName + "!" + Hello.class.getName();
        Properties props = new Properties();
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        InitialContext context = new InitialContext(props);
        java.util.logging.Logger.getLogger(HelloSLSBClient.class.getName()).fine("info");
        java.util.logging.Logger.getLogger(HelloSLSBClient.class.getName()).fine("fine");
        java.util.logging.Logger.getLogger(HelloSLSBClient.class.getName()).severe("severe");
        Hello hello = (Hello)context.lookup(jndiName);
        hello.hello();
    }

}
