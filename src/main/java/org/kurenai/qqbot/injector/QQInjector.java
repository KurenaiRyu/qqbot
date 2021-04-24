package org.kurenai.qqbot.injector;

import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.pojo.Event;

import java.util.Optional;

/**
 * @author Kurenai
 * @since 2021-04-21 16:27
 */

public class QQInjector implements Injector<Long> {

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

    @Override
    public Long getInjectObj(BotContext ctx) {
        Optional<Event> eventOpt = Optional.ofNullable(ctx.getEvent());
        return eventOpt.map(Event::getUserId).or(() -> eventOpt.map(Event::getSender).map(Event.Sender::getUserId)).orElse(0L);
    }


}
