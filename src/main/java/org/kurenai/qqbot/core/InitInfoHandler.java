package org.kurenai.qqbot.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.constant.Api;
import org.kurenai.qqbot.exception.BotException;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.Bot;
import org.kurenai.qqbot.pojo.result.*;
import org.kurenai.qqbot.util.JacksonFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Kurenai
 * @since 2021-04-02 15:59
 */

@Slf4j
public class InitInfoHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    private boolean initGroupFlag  = false;
    private boolean initFriendFlag = false;
    private boolean initBotFlag    = false;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        try {
            if (msg.text().contains("echo")) {
                BaseResult result = mapper.readValue(msg.text(), BaseResult.class);
                if ("group".equals(result.getEcho())) {
                    GroupInfo groupInfo = mapper.readValue(msg.text(), GroupInfo.class);
                    groupInfo.getData().forEach(data -> Global.GROUP_INFO_MAP.put(data.getGroupId(), data));
                    initGroupFlag = true;
                    log.info("Load {} groups.", Global.GROUP_INFO_MAP.size());
                } else {
                    Bot        bot        = Global.getBot(ctx);
                    Map<Long, FriendInfo.Data> qqMap = Global.QQ_INFO_MAP.getOrDefault(bot.getQq(), new HashMap<>());
                    if ("friend".equals(result.getEcho())) {
                        FriendInfo friendInfo = mapper.readValue(msg.text(), FriendInfo.class);
                        friendInfo.getData().forEach(data -> qqMap.put(data.getUserId(), data));
                        Global.QQ_INFO_MAP.put(
                                bot.getQq(),
                                qqMap);
                        initFriendFlag = true;
                        log.info("Load {} friends.", friendInfo.getData().size());
                    } else if ("login".equals(result.getEcho())) {
                        LoginInfo loginInfo = mapper.readValue(msg.text(), LoginInfo.class);
                        LoginInfo.Data data = loginInfo.getData();
                        bot.setNickname(data.getNickname());
                        log.info("Welcome {}({}).", data.getNickname(), data.getUserId());
                        initBotFlag = true;
                    }
                }
            } else if (initBotFlag && initFriendFlag && initGroupFlag) {
                ctx.pipeline().remove(this);
                log.debug("Init info success, remove this handler. now pipeline: {}", String.join(",", ctx.pipeline().names()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            initInfo(ctx, ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders());
        }
        super.userEventTriggered(ctx, evt);
    }

    private void initInfo(ChannelHandlerContext ctx, HttpHeaders headers) throws BotException {
        //Bot info
        Bot bot = Bot.builder().xClientRole(headers.get(BotConstant.X_CLIENT_ROLE))
                .qq(Long.parseLong(headers.get(BotConstant.X_SELF_ID)))
                .build();
        Global.putBot(ctx, bot);

        try {
            //Group info
            Action groupListAction = Action.builder().action(Api.GET_GROUP_LIST.getUrl()).params(Action.Param.builder().build()).echo("group").build().async();
            ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(groupListAction)))
                    .addListener(f -> log.info("Loading group..."));

            //Friend info
            Action friendListAction = Action.builder().action(Api.GET_FRIEND_LIST.getUrl()).params(Action.Param.builder().build()).echo("friend").build().async();
            ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(friendListAction)))
                    .addListener(f -> log.info("Loading friend..."));

            //Login info
            Action loginInfoAction = Action.builder().action(Api.GET_LOGIN_INFO.getUrl()).params(Action.Param.builder().build()).echo("login").build().async();
            ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(loginInfoAction)))
                    .addListener(f -> log.info("Loading login information..."));
        } catch (JsonProcessingException exception) {
            throw new BotException("Initialization bot({" + bot.getQq() + "}) information fail!");
//            log.error("Initialization bot({}) information fail!", bot.getQq(), exception);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
