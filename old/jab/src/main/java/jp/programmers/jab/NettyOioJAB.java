package jp.programmers.jab;

import java.util.concurrent.ExecutorService;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.oio.OioClientSocketChannelFactory;

public class NettyOioJAB extends NettyJAB {

    protected ChannelFactory createChannelFactory(ExecutorService executor) {
        return new OioClientSocketChannelFactory(executor);
    }

}
