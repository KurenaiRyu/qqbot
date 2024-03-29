package org.kurenai.qqbot.handle;

import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.constant.EventMessageType;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.Event;
import org.kurenai.qqbot.util.MsgChain;

/**
 * @author Kurenai
 * @since 2021-04-09 11:27
 */
public class TestHandler extends AbstractEventHandler {

    @Override
    public boolean match(BotContext ctx) {
        return EventMessageType.GROUP.equals(ctx.getEvent().getMessageType()) && ctx.regex("^location( \\d*[.]\\d*){2}$");
    }

    @Override
    public Action doHandle(BotContext ctx, Event event) {
        String[] s = ctx.getEvent().getRawMessage().split(" ");
        return ctx.sendGroupMsg(MsgChain.of("location test: ").location(Float.parseFloat(s[1]), Float.parseFloat(s[2])));
    }


}
