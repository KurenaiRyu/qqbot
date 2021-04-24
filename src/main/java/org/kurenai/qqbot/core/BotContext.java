package org.kurenai.qqbot.core;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kurenai.qqbot.constant.Api;
import org.kurenai.qqbot.constant.EventMessageType;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.result.ActionResult;
import org.kurenai.qqbot.pojo.Bot;
import org.kurenai.qqbot.pojo.Event;
import org.kurenai.qqbot.handle.EventHandler;
import org.kurenai.qqbot.util.MsgChain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kurenai
 * @since 2021-04-09 13:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotContext {

    private final Map<String, Object> context = new HashMap<>();

    private Bot                   bot;
    private Event                 event;
    private ChannelHandlerContext channelHandlerContext;
    private EventHandler eventHandler;
    private ActionResult response;

    public BotContext(ChannelHandlerContext ctx, Event event) {
        this.channelHandlerContext = ctx;
        this.event = event;
        this.bot = Global.getBot(ctx);
    }

    /////////////// send /////////////////

    public Action sendPrivateMsg(MsgChain msgChain) {
        return sendGroupMsg(event.getUserId(), msgChain);
    }

    public Action sendPrivateMsg(long qq, MsgChain msgChain) {
        Action.Param params = Action.Param.builder()
                .userId(qq)
                .message(msgChain)
                .build();
        return Action.builder()
                .action(Api.SEND_PRIVATE_MSG.getUrl())
                .params(params)
                .build();
    }

    public Action sendGroupMsg(String msg) {
        return sendGroupMsg(MsgChain.of(msg));
    }

    public Action sendGroupMsg(MsgChain msgChain) {
        return sendGroupMsg(event.getGroupId(), msgChain);
    }

    public Action sendGroupMsg(long groupId, String msg) {
        return sendGroupMsg(groupId, MsgChain.of(msg));
    }

    public Action sendGroupMsg(long groupId, MsgChain msgChain) {
        Action.Param params = Action.Param.builder()
                .groupId(groupId)
                .message(msgChain)
                .build();
        return Action.builder()
                .action(Api.SEND_GROUP_MSG.getUrl())
                .params(params)
                .build();
    }

    ////////////// match ///////////////////

    public boolean matchType(EventMessageType messageType) {
        return messageType.equals(event.getMessageType());
    }

    public boolean contains(String... strings) {
        String rawMessage = event.getRawMessage();
        for (String string : strings) {
            if (rawMessage.contains(string)) {
                return true;
            }
        }
        return false;
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


    //=================== get/set =======================

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) context.get(key);
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }
}
