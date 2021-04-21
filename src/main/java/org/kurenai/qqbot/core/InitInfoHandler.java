package org.kurenai.qqbot.core;

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
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.Bot;
import org.kurenai.qqbot.pojo.result.GroupInfo;
import org.kurenai.qqbot.util.JacksonFactory;

/**
 * @author Kurenai
 * @since 2021-04-02 15:59
 */

@Slf4j
public class InitInfoHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    private boolean initGroupFlag = false;
    private boolean initFriendFlag = false;
    private boolean initBotFlag = false;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        try {
            if (msg.text().contains("echo")) {
                GroupInfo groupInfo = mapper.readValue(msg.text(), GroupInfo.class);
                if ("group".equals(groupInfo.getEcho())) {
                    groupInfo.getData().forEach(data -> Global.GROUP_INFO_MAP.put(data.getGroupId(), data));
                    log.info("Load {} group information.", Global.GROUP_INFO_MAP.size());
                    count(ctx);
                }
                if (initBotFlag && initFriendFlag && initGroupFlag) ctx.pipeline().remove(this);
            } else {
                ctx.fireChannelRead(msg.retain());
            }
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            ctx.fireChannelRead(msg.retain());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            initInfo(ctx, ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders());
        }
        super.userEventTriggered(ctx, evt);
    }

    private void initInfo(ChannelHandlerContext ctx, HttpHeaders headers) {
        //Bot info
        Bot info = Bot.builder().xClientRole(headers.get(BotConstant.X_CLIENT_ROLE))
                .xSelfId(headers.get(BotConstant.X_SELF_ID))
                .build();
        Global.putBot(ctx, info);

        try {
            //Group info
            Action groupListAction = Action.builder().action(Api.GET_GROUP_LIST.getUrl()).params(Action.Param.builder().build()).echo("group").build().async();
            ChannelFuture future = ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(groupListAction)));
            future.addListener(f -> log.info("Loading group info..."));


            Action friendListAction = Action.builder().action(Api.GET_FRIEND_LIST.getUrl()).params(Action.Param.builder().build()).echo("group").build().async();
            ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(groupListAction)));
        } catch (JsonProcessingException exception) {
            log.error("Initialization bot({}) information fail!", info.getXSelfId(), exception);
        }
    }

    private void count(ChannelHandlerContext ctx) {
    }
}
