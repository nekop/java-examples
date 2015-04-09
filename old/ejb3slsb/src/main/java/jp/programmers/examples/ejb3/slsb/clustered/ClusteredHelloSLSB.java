package jp.programmers.examples.ejb3.slsb.clustered;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import jp.programmers.examples.ejb3.slsb.HelloSLSB;
import org.jboss.ejb3.annotation.Clustered;

@Remote
@Stateless
@org.jboss.ejb3.annotation.Clustered
//@org.jboss.ejb3.annotation.Clustered(loadBalancePolicy="FirstAvailableIdenticalAllProxies")
public class ClusteredHelloSLSB extends HelloSLSB {

}
