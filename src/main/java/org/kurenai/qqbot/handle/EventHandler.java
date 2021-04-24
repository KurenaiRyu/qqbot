package org.kurenai.qqbot.handle;

import org.kurenai.qqbot.core.BotContext;

/**
 * @author Kurenai
 * @since 2021-04-08 11:54
 */
public interface EventHandler {

    String handle(BotContext ctx);

    void onResponse(BotContext ctx);

    boolean isContinue();

    boolean isHandleResponse();

    int order();


}
