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

    public static void main(String[] args) throws Exception {
        ChannelListener<ConnectedStreamChannel> serverListener = new ChannelListener<ConnectedStreamChannel>() {
            String lastRead = "";
            public void handleEvent(final ConnectedStreamChannel channel) {
                System.out.println("opened channel: " + channel);
                channel.getCloseSetter().set(new ChannelListener<ConnectedStreamChannel>() {
                        public void handleEvent(final ConnectedStreamChannel channel) {
                            System.out.println("closed channel: " + channel);
                        }
                    });
                channel.getReadSetter().set(new ChannelListener<ConnectedStreamChannel>() {
                        public void handleEvent(final ConnectedStreamChannel channel) {
                            try {
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                channel.read(buffer);
                                buffer.clear();
                                byte[] bytes = new byte[buffer.capacity()];
                                buffer.get(bytes);
                                lastRead = new String(bytes, "UTF-8");
                                System.out.println("Read: " + lastRead);
                                channel.resumeWrites();
                            } catch (Throwable t) {
                                throw new RuntimeException("read error", t);
                            }
                        }
                    });
                channel.getWriteSetter().set(new ChannelListener<ConnectedStreamChannel>() {
                        public void handleEvent(final ConnectedStreamChannel channel) {
                            try {
                                System.out.println("Write: " + lastRead);
                                ByteBuffer buffer = ByteBuffer.wrap(lastRead.getBytes("UTF-8"));
                                channel.write(buffer);
                                channel.close();
                            } catch (Throwable t) {
                                throw new RuntimeException("write error", t);
                            }
                        }
                    });
                channel.resumeReads();
            }
        };

        Xnio xnio =
            Xnio.getInstance();
        XnioWorker worker =
            xnio.createWorker(OptionMap.create(Options.WORKER_WRITE_THREADS, 2, Options.WORKER_READ_THREADS, 2));
        InetSocketAddress address =
            new InetSocketAddress(Inet4Address.getByName(SERVER_HOST), SERVER_PORT);
        ChannelListener<? super AcceptingChannel<ConnectedStreamChannel>> acceptListener =
            ChannelListeners.<ConnectedStreamChannel>openListenerAdapter(serverListener);
        OptionMap optionMap =
            OptionMap.create(Options.REUSE_ADDRESSES, Boolean.TRUE);

        AcceptingChannel<? extends ConnectedStreamChannel> server =
            worker.createStreamServer(address,
                                      acceptListener,
                                      OptionMap.create(Options.REUSE_ADDRESSES, Boolean.TRUE));

        server.resumeAccepts();

        //server.close();
    }
}
