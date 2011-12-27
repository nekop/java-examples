package jp.programmers.jab;

import java.util.concurrent.ExecutorService;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class NettyNioJAB extends NettyJAB {

    protected ChannelFactory createChannelFactory(ExecutorService executor) {
        return new NioClientSocketChannelFactory(executor, executor);
    }

}
