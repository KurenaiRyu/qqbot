package org.kurenai.qqbot;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kurenai
 * @since 2021-04-02 15:59
 */

@Slf4j
public class BotInfoHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String path;

    public BotInfoHandler(String path) {
        this.path = path;
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (request.uri().equals(path)) {
            Bot info = Bot.builder().xClientRole(request.headers()
                                                         .get(BotConstant.X_CLIENT_ROLE))
                    .xSelfId(request.headers().get(BotConstant.X_SELF_ID))
                    .build();
            GlobalHolder.putBot(ctx, info);
            ctx.fireChannelRead(request.retain());
        }
        log.info(ctx.channel().id().asLongText());
    }
}
