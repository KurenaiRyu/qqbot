package org.kurenai.qqbot;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.domain.GroupInfo;
import org.kurenai.qqbot.util.JacksonFactory;

import java.util.List;

/**
 * @author Kurenai
 * @since 2021-04-02 15:59
 */

@Slf4j
public class GroupInfoHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        try {
            List<GroupInfo> groupInfos = mapper.readValue(msg.text(), new TypeReference<>() {
            });
            for (GroupInfo groupInfo : groupInfos) {
                Global.GROUP_INFO_MAP.put(groupInfo.getGroupId(), groupInfo);
            }
            ctx.pipeline().remove(this);
        } catch (JsonParseException e) {
            ctx.fireChannelRead(msg.retain());
        }
    }
}
