package org.kurenai.qqbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.constant.Api;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.domain.Bot;
import org.kurenai.qqbot.domain.Event;
import org.kurenai.qqbot.handle.BotEventHandler;
import org.kurenai.qqbot.util.JacksonFactory;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//处理文本协议数据，处理TextWebSocketFrame类型的数据，websocket专门处理文本的frame就是TextWebSocketFrame
@Slf4j
public class TextWebSocketFrameHandle extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Event event = null;
        log.trace(msg.text());
        try {
            event = mapper.readValue(msg.text(), Event.class);

        } catch (Exception e) {
            log.warn("Parse error! {}", msg.text());
        }
        if (event == null) return;

        ExecutorService pool = Executors.newFixedThreadPool(1);
        BotContext botContext = new BotContext(ctx, event);
        pool.execute(() -> {
            for (BotEventHandler handler : Global.HANDLERS) {
                //TODO: break
                Optional.ofNullable(handler.handle(botContext))
                        .map(TextWebSocketFrame::new)
                        .ifPresent(ctx::writeAndFlush);
                if (!handler.isContinue()) break;
            }
        });


        /**
         * writeAndFlush接收的参数类型是Object类型，但是一般我们都是要传入管道中传输数据的类型，比如我们当前的demo
         * 传输的就是TextWebSocketFrame类型的数据
         */
//        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务时间：" + LocalDateTime.now()));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            HttpHeaders headers = handshakeComplete.requestHeaders();
            log.info("New Client joined: {}|{}|{}", headers, handshakeComplete.requestUri(), handshakeComplete.selectedSubprotocol());

            initInfo(ctx, headers);


//            ctx.pipeline().remove(InitBotInfoHandler.class);
        }
        super.userEventTriggered(ctx, evt);
    }

    //每个channel都有一个唯一的id值
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //打印出channel唯一值，asLongText方法是channel的id的全名

        log.info("handlerAdded：" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Global.removeBot(ctx);
        log.info("handlerRemoved：" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
//        ctx.close();
    }

    private void initInfo(ChannelHandlerContext ctx, HttpHeaders headers) {
        //Bot info
        Bot info = Bot.builder().xClientRole(headers.get(BotConstant.X_CLIENT_ROLE))
                .xSelfId(headers.get(BotConstant.X_SELF_ID))
                .build();
        Global.putBot(ctx, info);

        Action action = Action.builder().action(Api.GET_GROUP_LIST.getUrl()).build();

        //Group info
        try {
            ChannelFuture future = ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(action)));
            future.addListener(f -> ctx.pipeline().addBefore("eventHandler", "groupInfoHandler", new GroupInfoHandler()));
        } catch (JsonProcessingException exception) {
            log.error("发送获取群组信息请求失败", exception);
        }
    }
}