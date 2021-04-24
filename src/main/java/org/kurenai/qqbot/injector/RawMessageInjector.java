package org.kurenai.qqbot.injector;

import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.pojo.Event;

import java.util.Optional;

/**
 * @author Kurenai
 * @since 2021-04-21 16:27
 */

public class RawMessageInjector implements Injector<String> {

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String getInjectObj(BotContext ctx) {
        return Optional.ofNullable(ctx.getEvent()).map(Event::getRawMessage).orElse("");
    }


}
