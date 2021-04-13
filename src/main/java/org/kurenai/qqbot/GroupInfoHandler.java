package org.kurenai.qqbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.constant.Api;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.domain.GroupInfo;
import org.kurenai.qqbot.util.JacksonFactory;

/**
 * @author Kurenai
 * @since 2021-04-02 15:59
 */

@Slf4j
public class GroupInfoHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        if (msg.text().contains("group_memo")) {
            //TODO: 信息被截断
            log.info("Get echo!!!!! {}", msg.text());
            GroupInfo groupInfo = mapper.readValue(msg.text(), GroupInfo.class);
            System.out.println(groupInfo);
        } else {
            if (Math.random() > 0.7) {
                Action action = Action.builder().action(Api.GET_GROUP_LIST.getUrl()).params(Action.Param.builder().noCache(false).build()).echo("group").build();

                //Group info
                try {
                    String json = mapper.writeValueAsString(action);
                    ChannelFuture future = ctx.writeAndFlush(new TextWebSocketFrame(json));

                    future.addListener(f -> log.info("send request {}", json));
                } catch (JsonProcessingException exception) {
                    log.error("发送获取群组信息请求失败", exception);
                }
            }
        }
//        try {
//            GroupInfo groupInfo = mapper.readValue(msg.text(), new TypeReference<>() {
//            });
//            System.out.println(groupInfo);
//            if (!"group".equals(groupInfo.getEcho())) {
//                if (Math.random() > 0.7) {
//                    Action action = Action.builder().action(Api.GET_GROUP_LIST.getUrl()).params(Action.Param.builder().groupId(213690137L).build()).echo("group").build();
//
//                    //Group info
//                    try {
//                        ChannelFuture future = ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(action)));
//
//                        future.addListener(f -> log.info("send request"));
//                    } catch (JsonProcessingException exception) {
//                        log.error("发送获取群组信息请求失败", exception);
//                    }
//                } else
//                    ctx.fireChannelRead(msg.retain());
//            }
//            else {
//                Global.GROUP_INFO_MAP.put(groupInfo.getGroupId(), groupInfo);
//                ctx.pipeline().remove(this);
//            }
//        } catch (Exception e) {
//
//            if (Math.random() > 0.7) {
//                Action action = Action.builder().action(Api.GET_GROUP_INFO.getUrl()).params(Action.Param.builder().groupId(213690137L).build()).build();
//
//                //Group info
//                try {
//                    ChannelFuture future = ctx.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(action)));
//
//                    future.addListener(f -> log.info("send request"));
//                } catch (JsonProcessingException exception) {
//                    log.error("发送获取群组信息请求失败", exception);
//                }
//            } else {
//                ctx.fireChannelRead(msg.retain());
//            }
//        }
    }
}
