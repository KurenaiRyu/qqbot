package org.kurenai.qqbot.handle;

import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.BotContext;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.domain.Event;
import org.kurenai.qqbot.util.MessageBuilder;

/**
 * @author Kurenai
 * @since 2021-04-08 14:57
 */

@Slf4j
public class PingHandler extends AbstractBotEventHandler {

    @Override
    public boolean match(BotContext ctx) {
        return ctx.matchCommand("ping");
    }

    @Override
    public Action doHandle(BotContext ctx) {
        Event event = ctx.getEvent();
        return Action.builder()
                .action(Action.SEND_MSG)
                .params(Action.Param.builder()
                                .userId(event.getGroupId() == null ? event.getUserId() : null)
                                .groupId(event.getGroupId())
                                .message(MessageBuilder.of(event.getUserId())
                                                 .text("Pong!")
                                                 .build())
                                .build())
                .build()
                .async();
    }
}