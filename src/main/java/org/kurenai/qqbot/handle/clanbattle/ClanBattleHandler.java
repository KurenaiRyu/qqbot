package org.kurenai.qqbot.handle.clanbattle;

import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.annotation.GroupEvent;
import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.constant.EventMessageType;
import org.kurenai.qqbot.core.Global;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.Event;
import org.kurenai.qqbot.handle.AbstractEventHandler;
import org.kurenai.qqbot.util.MessageBuilder;

/**
 * @author Kurenai
 * @since 2021-04-09 11:27
 */

@Slf4j
public class ClanBattleHandler extends AbstractEventHandler {

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

    @GroupEvent(command = "create clan")
    public Action createClan(BotContext ctx, Event event) {
        log.info("Create a clan for {}({})", Global.GROUP_INFO_MAP.get(event.getGroupId()), event.getGroupId());
        return null;
    }
}
