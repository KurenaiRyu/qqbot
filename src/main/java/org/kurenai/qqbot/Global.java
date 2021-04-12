package org.kurenai.qqbot;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kurenai.qqbot.domain.Bot;
import org.kurenai.qqbot.domain.GroupInfo;
import org.kurenai.qqbot.handle.BotEventHandler;
import org.kurenai.qqbot.handle.PingHandler;
import org.kurenai.qqbot.handle.TestHandler;
import org.kurenai.qqbot.handle.clanbattle.ClanBattleHandler;

import java.util.*;

/**
 * @author Kurenai
 * @since 2021-04-02 14:21
 */

@Getter
@Setter
@Slf4j
public class Global {

    public static final List<BotEventHandler>   HANDLERS           = new ArrayList<>();
    public static final Map<String, BotContext> BOT_CONTEXT_MAP    = new HashMap<>();
    public static final Map<Long, GroupInfo>    GROUP_INFO_MAP     = new HashMap<>();
    public static final HashMap<String, Object> CLASS_INSTANCE_MAP = new HashMap<>();

    static {
        HANDLERS.add(new PingHandler());
        HANDLERS.add(new ClanBattleHandler());
        HANDLERS.add(new TestHandler());
    }

    private static final Map<String, Bot> BOT       = new HashMap<>();
    private static final Bot              EMPTY_BOT = new Bot();

    public static long id = 0;

    private Global() {

    }

    public static Bot getBot(ChannelHandlerContext ctx) {
        return getContextId(ctx)
                .map(BOT::get)
                .orElse(EMPTY_BOT);
    }

    public static void putBot(ChannelHandlerContext ctx, Bot bot) {
        getContextId(ctx).ifPresent(id -> putBot(id, bot));
    }

    public static void putBot(String idText, Bot bot) {
        if (bot != null && StringUtils.isNotBlank(idText)) {
            BOT.put(idText, bot);
            log.info("Put {}", bot);
        }
    }

    public static void removeBot(ChannelHandlerContext ctx) {
        getContextId(ctx).ifPresent(Global::removeBot);
    }

    public static void removeBot(String idText) {
        log.info("Remove {}", BOT.remove(idText));
    }

    private static Optional<String> getContextId(ChannelHandlerContext ctx) {
        return Optional.ofNullable(ctx)
                .map(ChannelHandlerContext::channel)
                .map(Channel::id)
                .map(ChannelId::asLongText);
    }

}
