package jp.programmers.xnio3.examples;

import org.xnio.Xnio;
import org.xnio.XnioWorker;
import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.ChannelListener;
import org.xnio.ChannelListeners;
import org.xnio.channels.ConnectedStreamChannel;
import org.xnio.channels.AcceptingChannel;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class EchoServer {

    static String SERVER_HOST = "127.0.0.1";
    static int SERVER_PORT = 54565;

    static class EchoServerListener implements ChannelListener<ConnectedStreamChannel> {
        String lastRead = "";
        public void handleEvent(final ConnectedStreamChannel channel) {
            log("opened channel: " + channel);
            channel.getReadSetter().set(new ReadListener());
            channel.getWriteSetter().set(new WriteListener());
            channel.getCloseSetter().set(new CloseListener());
            channel.resumeReads();
        }

        class ReadListener implements ChannelListener<ConnectedStreamChannel> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            public void handleEvent(final ConnectedStreamChannel channel) {
                try {
                    channel.read(buffer);
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    buffer.clear();
                    lastRead = new String(bytes, "UTF-8");
                    log("Read: " + lastRead);
                    channel.resumeWrites();
                } catch (Throwable t) {
                    throw new RuntimeException("read error", t);
                }
            }
        }
        class WriteListener implements ChannelListener<ConnectedStreamChannel> {
            public void handleEvent(final ConnectedStreamChannel channel) {
                try {
                    log("Write: " + lastRead);
                    ByteBuffer buffer = ByteBuffer.wrap(lastRead.getBytes("UTF-8"));
                    channel.write(buffer);
                    channel.close();
                } catch (Throwable t) {
                    throw new RuntimeException("write error", t);
                }
            }
        }
        class CloseListener implements ChannelListener<ConnectedStreamChannel> {
            public void handleEvent(final ConnectedStreamChannel channel) {
                log("closed channel: " + channel);
            }
        }
    }

    static void log(String message) {
        System.out.println("Thread: " + Thread.currentThread() + ", " + message);
    }

    public static void main(String[] args) throws Exception {
        Xnio xnio =
            Xnio.getInstance();
        XnioWorker worker =
            xnio.createWorker(OptionMap.create(Options.WORKER_WRITE_THREADS, 2, Options.WORKER_READ_THREADS, 2));
        InetSocketAddress address =
            new InetSocketAddress(Inet4Address.getByName(SERVER_HOST), SERVER_PORT);
        ChannelListener<? super AcceptingChannel<ConnectedStreamChannel>> acceptListener =
            ChannelListeners.<ConnectedStreamChannel>openListenerAdapter(new EchoServerListener());
        OptionMap optionMap =
            OptionMap.create(Options.REUSE_ADDRESSES, Boolean.TRUE);

        AcceptingChannel<? extends ConnectedStreamChannel> server =
            worker.createStreamServer(address,
                                      acceptListener,
                                      optionMap);

        server.resumeAccepts();

        //server.close();
    }
}
