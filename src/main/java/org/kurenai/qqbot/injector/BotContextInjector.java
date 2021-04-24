package org.kurenai.qqbot.injector;

import org.kurenai.qqbot.core.BotContext;

/**
 * @author Kurenai
 * @since 2021-04-21 16:27
 */

public class BotContextInjector implements Injector<BotContext> {

    @Override
    public Class<BotContext> getType() {
        return BotContext.class;
    }

    @Override
    public BotContext getInjectObj(BotContext ctx) {
        return ctx;
    }


}
