package org.kurenai.qqbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.kurenai.qqbot.domain.ActionResponse;
import org.kurenai.qqbot.util.JacksonFactory;

/**
 * @author Kurenai
 * @since 2021-04-09 15:39
 */
public class ResponseHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        if (GlobalHolder.HANDLER_CONTEXT_MAP.size() > 0) {
            ActionResponse res = mapper.readValue(msg.text(), ActionResponse.class);
            HandlerContext handlerContext = GlobalHolder.HANDLER_CONTEXT_MAP.get(res.getEcho());
            handlerContext.getEventHandler().handleResponse(handlerContext);
        }
    }
}
