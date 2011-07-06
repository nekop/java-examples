package jp.programmers.examples.ejb3.slsb;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

public class HelloSLSBClient {

    public static final String DEFAULT_PROVIDER_URL = "localhost:1099";

    public static void main(String... args) throws Exception {
        String providerUrl = DEFAULT_PROVIDER_URL;
        if (args.length != 0) {
            providerUrl = args[0];
        }

        String jndiName = "HelloSLSB/remote";
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,
                  "org.jnp.interfaces.NamingContextFactory");
        props.put(Context.URL_PKG_PREFIXES,
                  "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, providerUrl);
        InitialContext context = new InitialContext(props);
        Hello hello = (Hello)context.lookup(jndiName);
        hello.hello();
    }

}
