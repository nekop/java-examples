package jp.programmers.examples.ejb3.startup;

import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.annotation.PostConstruct;

@Startup
@Singleton
public class StartupBean {

    @PostConstruct
    public void init() {
        System.out.println("StartupBean.init()");
    }

}
