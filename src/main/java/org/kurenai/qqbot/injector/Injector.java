package org.kurenai.qqbot.injector;

import org.kurenai.qqbot.core.BotContext;

/**
 * @author Kurenai
 * @since 2021-04-21 16:20
 */

public interface Injector<T> {

    Class<T> getType();

    Object getInjectObj(BotContext ctx);
}
