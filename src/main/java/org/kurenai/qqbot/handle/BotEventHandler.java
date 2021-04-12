package org.kurenai.qqbot.handle;

import org.kurenai.qqbot.BotContext;

/**
 * @author Kurenai
 * @since 2021-04-08 11:54
 */
public interface BotEventHandler {

    String handle(BotContext ctx);

    void onResponse(BotContext ctx);

    default boolean isContinue() {
        return true;
    }
}
