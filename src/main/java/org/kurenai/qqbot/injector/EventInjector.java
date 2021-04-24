package org.kurenai.qqbot.injector;

import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.pojo.Event;

/**
 * @author Kurenai
 * @since 2021-04-21 16:27
 */

public class EventInjector implements Injector<Event> {

    @Override
    public Class<Event> getType() {
        return Event.class;
    }

    @Override
    public Event getInjectObj(BotContext ctx) {
        return ctx.getEvent();
    }


}
