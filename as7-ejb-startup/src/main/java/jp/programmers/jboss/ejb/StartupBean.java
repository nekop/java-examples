package jp.programmers.jboss.ejb;

import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.ejb.EJB;
import javax.annotation.PostConstruct;

@Startup
@Singleton
public class StartupBean {

    @EJB
    private StatefulBean sfsb;

    @PostConstruct
    public void init() {
        System.out.println("StatefulBean.init()");
        sfsb.test();
    }

}
