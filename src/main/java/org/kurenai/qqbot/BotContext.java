package org.kurenai.qqbot;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kurenai.qqbot.constant.Api;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.domain.Event;
import org.kurenai.qqbot.util.MessageBuilder;

/**
 * @author Kurenai
 * @since 2021-04-09 13:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotContext {

    private Bot                   bot;
    private Event                 event;
    private ChannelHandlerContext channelHandlerContext;

    public BotContext(ChannelHandlerContext ctx, Event event) {
        this.channelHandlerContext = ctx;
        this.event = event;
        this.bot = GlobalHolder.getBot(ctx);
    }

    public Action sendGroupMsg(MessageBuilder messageBuilder) {
        return sendGroupMsg(event.getGroupId(), messageBuilder);
    }

    public Action sendGroupMsg(long groupId, MessageBuilder messageBuilder) {
        Action.Param params = Action.Param.builder()
                .groupId(groupId)
                .message(messageBuilder.build())
                .build();
        return Action.builder()
                .action(Api.SEND_GROUP_MSG.getUrl())
                .params(params)
                .build();
    }

    public boolean matchCommand(String... commands) {
        return matchCommand(true, commands);
    }

    public boolean matchCommand(boolean ignoreCase, String... commands) {
        String rawMessage = event.getRawMessage();
        if (rawMessage == null) return false;
        for (String command : commands) {
            if (ignoreCase && command.equalsIgnoreCase(rawMessage) || command.equals(rawMessage)) {
                return true;
            }
        }
        return false;
    }

    public boolean regex(String regex) {
        String rawMessage = event.getRawMessage();
        if (rawMessage == null) return false;
        return rawMessage.matches(regex);
    }
}
