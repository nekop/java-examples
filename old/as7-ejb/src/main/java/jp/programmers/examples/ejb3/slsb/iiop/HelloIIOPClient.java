package jp.programmers.examples.ejb3.slsb.iiop;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class HelloIIOPClient {

    public static final String DEFAULT_PROVIDER_URL = "corbaloc:iiop:localhost:3528/JBoss/Naming/root";

    public static void main(String... args) throws Exception {
        String providerUrl = DEFAULT_PROVIDER_URL;
        if (args.length != 0) {
            // We can use IOR for naming service like:
            //providerUrl = "IOR:000000000000002B49444C3A6F6D672E6F72672F436F734E616D696E672F4E616D696E67436F6E746578744578743A312E3000000000000100000000000000B0000102000000000A3132372E302E302E31000DC8000000114A426F73732F4E616D696E672F726F6F74000000000000040000000000000008000000004A4143000000000100000020000000000501000100000001000100010001010900000002050100010001010000000014000000080000001A00000DC90000002100000030000000000000000100000000000000220000000000000000000000000000000000000000000000000000000000000000";
            providerUrl = args[0];
        }

        System.setProperty("com.sun.CORBA.ORBUseDynamicStub", "true");
        String jndiName = "HelloIIOP";
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,
                  "com.sun.jndi.cosnaming.CNCtxFactory");
        props.put(Context.PROVIDER_URL, providerUrl);
        InitialContext context = new InitialContext(props);
        Object o = context.lookup(jndiName);

        // Or
        //InitialContext context = new InitialContext();
        //Object o = context.lookup("corbaname:iiop:localhost:3528#HelloIIOP");

        System.out.println(o);
        HelloHome helloHome =
            (HelloHome)PortableRemoteObject.narrow(o, HelloHome.class);
        HelloRemote hello = helloHome.create();
        System.out.println(hello.hello());
    }

}
