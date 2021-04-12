package org.kurenai.qqbot.handle.clanbattle;

import org.kurenai.qqbot.BotContext;
import org.kurenai.qqbot.constant.EventMessageType;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.domain.Event;
import org.kurenai.qqbot.handle.AbstractBotEventHandler;
import org.kurenai.qqbot.util.MessageBuilder;

/**
 * @author Kurenai
 * @since 2021-04-09 11:27
 */
public class ClanBattleHandler extends AbstractBotEventHandler {

    @Override
    public boolean match(BotContext ctx) {
        return EventMessageType.GROUP.equals(ctx.getEvent().getMessageType());
    }

    @Override
    public Action doHandle(BotContext ctx, Event event) {
        if (ctx.matchCommand("status")) {
            return ctx.sendGroupMsg(MessageBuilder.of("Play now!"));
        }
        return null;
    }

    @Override
    public void onResponse(BotContext ctx) {

    }
}
