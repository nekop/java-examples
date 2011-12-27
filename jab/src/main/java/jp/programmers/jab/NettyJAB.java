package jp.programmers.jab;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.oio.OioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpClientCodec;
import org.jboss.netty.handler.codec.http.HttpContentDecompressor;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.logging.InternalLogLevel;

public abstract class NettyJAB extends BaseJAB {

    ExecutorService executor;
    ClientBootstrap bootstrap;
    HttpClientPipelineFactory pipelineFactory;
    InetSocketAddress address;
    URI uri;
    String scheme;
    String host;
    int port;
    AtomicInteger numCount = new AtomicInteger();

    public void init(JABOptions options) throws Exception {
        super.init(options);
        if (options.getThreads() > 0) {
            executor = Executorz.newFixedThreadPoolAndPrefill(options.getThreads());
        } else {
            executor = Executorz.newFixedThreadPoolAndPrefill(options.getConcurrency());
        }
        uri = new URI(options.getArguments().get(0));
        scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        host = uri.getHost() == null ? "localhost" : uri.getHost();
        port = uri.getPort();
        if (port == -1) {
            if (scheme.equalsIgnoreCase("http")) {
                port = 80;
            }
        }
        address = new InetSocketAddress(host, port);
        bootstrap = new ClientBootstrap(createChannelFactory(executor));
        pipelineFactory = new HttpClientPipelineFactory();
        bootstrap.setPipelineFactory(pipelineFactory);

    }

    protected abstract ChannelFactory createChannelFactory(ExecutorService executor);

    protected void fire(int num) throws Exception {
        numCount.set(0);
        CountDownLatch numLatch = new CountDownLatch(num);
        pipelineFactory.latch = numLatch;
        // Note this loop should be concurrency, not num
        int concurrency = options.getConcurrency();
        for (int i = 0; i < concurrency; i++) {
            submit();
        }
        numLatch.await(300, TimeUnit.SECONDS);
    }

    private void submit() {
        if (numCount.incrementAndGet() <= options.getNum()) {
            ChannelFuture future = bootstrap.connect(address);
        }
    }

    protected void end() throws Exception {
        bootstrap.releaseExternalResources();
    }

    class HttpClientPipelineFactory implements ChannelPipelineFactory {
        CountDownLatch latch;
        @Override
        public ChannelPipeline getPipeline() throws Exception {
            ChannelPipeline pipeline = Channels.pipeline();
            //pipeline.addLast("log", new LoggingHandler(InternalLogLevel.INFO));
            pipeline.addLast("codec", new HttpClientCodec());
            pipeline.addLast("inflater", new HttpContentDecompressor());
            JABHandler jabHandler = new JABHandler();
            jabHandler.latch = this.latch;
            pipeline.addLast("handler", jabHandler);
            return pipeline;
        }
    }

    class JABHandler extends SimpleChannelHandler {
        CountDownLatch latch;
        long start = 0;
        boolean chunked = false;
        boolean finish = false;
        boolean error = false;

        public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            start = System.currentTimeMillis();
        }

        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            Channel channel = e.getChannel();
            HttpRequest request =
                new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
            request.setHeader(HttpHeaders.Names.HOST, host);
            request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            //request.setHeader(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
            channel.write(request);
        }

        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
            if (chunked) {
                HttpChunk chunk = (HttpChunk) e.getMessage();
                if (chunk.isLast()) {
                    finish = chunk.isLast();
                }
                return;
            }
            HttpResponse msg = (HttpResponse)e.getMessage();
            int statusCode = msg.getStatus().getCode();
            if (statusCode < 200 || 299 < statusCode) {
                error = true;
                Recorder.instance.error();
            }
            chunked = msg.isChunked();
            if (!chunked) {
                finish = true;
            }
        }

        public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            long end = System.currentTimeMillis();
            if (finish && !error) {
                Recorder.instance.success(end - start);
            }
            if (latch != null) {
                latch.countDown();
            }
            submit();
        }

        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
            error = true;
            e.getCause().printStackTrace();
            Recorder.instance.error();
        }
    }

}
