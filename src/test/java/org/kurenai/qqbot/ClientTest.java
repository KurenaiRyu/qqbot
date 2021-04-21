package org.kurenai.qqbot;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Kurenai
 * @since 2021-04-16 19:12
 */

@Slf4j
public class ClientTest {

    @Test
    void clientTest() {
        int port = 20010;
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            Channel channel = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();

                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块的方式来写的处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
                            pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 10));

                            String path = "/ws";
                            //ws://server:port/context_path
                            //ws://localhost:9999/ws
                            //参数指的是context_path
                            //pipeline.addLast(new InitBotInfoHandler(path));
                            pipeline.addLast(new WebSocketServerProtocolHandler(path));
                            //websocket定义了传递数据的6中frame类型
                            pipeline.addLast("test", new TestHandler());
                        }
                    })
                    .connect("localhost", 20010).channel();

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
