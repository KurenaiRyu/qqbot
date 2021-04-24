package org.kurenai.qqbot.handle.clanbattle;

import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.annotation.GroupEvent;
import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.core.Global;
import org.kurenai.qqbot.pojo.Event;

/**
 * @author Kurenai
 * @since 2021-04-09 11:27
 */

@Slf4j
public class ClanBattleHandler {

    @GroupEvent(command = "create clan")
    public String createClan(BotContext ctx, Event event) {
        log.info("Create a clan for {}({})", Global.GROUP_INFO_MAP.get(event.getGroupId()).getGroupName(), event.getGroupId());
        return "Create clan success";
    }
}
