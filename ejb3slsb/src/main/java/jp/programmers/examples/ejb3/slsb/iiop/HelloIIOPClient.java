package jp.programmers.examples.ejb3.slsb.iiop;

import java.rmi.RMISecurityManager;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class HelloIIOPClient {

    public static final String DEFAULT_PROVIDER_URL = "corbaloc:iiop:localhost:3528/JBoss/Naming/root";

    public static void main(String... args) throws Exception {
        String providerUrl = DEFAULT_PROVIDER_URL;
        if (args.length != 0) {
            providerUrl = args[0];
        }

        String jndiName = "HelloIIOP/home";
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,
                  "com.sun.jndi.cosnaming.CNCtxFactory");
        props.put(Context.PROVIDER_URL, providerUrl);
        InitialContext context = new InitialContext(props);
        Object o = context.lookup(jndiName);
        HelloHome helloHome =
            (HelloHome)PortableRemoteObject.narrow(o, HelloHome.class);
        HelloRemote hello = helloHome.create();
        System.out.println(hello.hello());
    }

}
