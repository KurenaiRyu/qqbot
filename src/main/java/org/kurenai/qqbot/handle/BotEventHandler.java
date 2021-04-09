package org.kurenai.qqbot.handle;

import org.kurenai.qqbot.BotContext;
import org.kurenai.qqbot.HandlerContext;

/**
 * @author Kurenai
 * @since 2021-04-08 11:54
 */
public interface BotEventHandler {

    String handle(BotContext ctx);

    void handleResponse(HandlerContext ctx);
}
