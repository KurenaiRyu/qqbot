package io.github.kurenairyu.qqbot.handle;

import io.github.kurenairyu.qqbot.domain.Event;

/**
 * @author Kurenai
 * @since 2021-04-08 11:54
 */
public interface EventHandler {

    String handle(Event event);


}
