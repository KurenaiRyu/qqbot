package org.kurenai.qqbot.handle.clanbattle;

import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.BotContext;
import org.kurenai.qqbot.Global;
import org.kurenai.qqbot.annotation.BotHandler;
import org.kurenai.qqbot.annotation.GroupEvent;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.domain.Event;

/**
 * @author Kurenai
 * @since 2021-04-12 11:27
 */
@BotHandler("clan battle")
@Slf4j
public class Handler {

    @GroupEvent(command = "create clan")
    public Action createClan(BotContext ctx, Event event) {
        log.info("Create a clan for {}({})", Global.GROUP_INFO_MAP.get(event.getGroupId()), event.getGroupId());
        return null;
    }

}
