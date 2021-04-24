package org.kurenai.qqbot.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kurenai.qqbot.pojo.Bot;
import org.kurenai.qqbot.pojo.result.FriendInfo;
import org.kurenai.qqbot.pojo.result.GroupInfo;
import org.kurenai.qqbot.handle.EventHandler;
import org.kurenai.qqbot.handle.PingHandler;
import org.kurenai.qqbot.handle.TestHandler;
import org.kurenai.qqbot.handle.clanbattle.ClanBattleHandler;
import org.kurenai.qqbot.injector.Injector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Kurenai
 * @since 2021-04-02 14:21
 */

@Getter
@Setter
@Slf4j
public class Global {

    public static final List<EventHandler>                    HANDLERS                   = new ArrayList<>();
    public static final Map<String, BotContext>               BOT_CONTEXT_MAP            = new HashMap<>();
    public static final Map<Long, GroupInfo.Data>             GROUP_INFO_MAP             = new HashMap<>();
    public static final Map<Long, Map<Long, FriendInfo.Data>> QQ_INFO_MAP                = new HashMap<>();
    public static final HashMap<Class<?>, Object>             HANDLER_CLASS_INSTANCE_MAP = new HashMap<>();
    public static final HashMap<Class<?>, Injector<?>>        INJECTOR_INSTANCE_MAP      = new HashMap<>();

    static {
        HANDLERS.add(new PingHandler());
        HANDLERS.add(new TestHandler());
    }

    private static final Map<String, Bot> BOT       = new HashMap<>();
    private static final Bot              EMPTY_BOT = new Bot();

    public static long id = 0;

    private Global() {

    }

    public static Object getClassInstance(Method method) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz    = method.getDeclaringClass();
        Object   instance = HANDLER_CLASS_INSTANCE_MAP.get(clazz);
        if (instance == null) {
            instance = method.getDeclaringClass().getConstructor().newInstance();
            HANDLER_CLASS_INSTANCE_MAP.put(clazz, instance);
        }
        return instance;
    }

    public static Object[] getInjectObjects(BotContext ctx, Class<?>[] classes) {
        Object[] objects = new Object[classes.length];
        for (int i = 0; i < classes.length; i++) {
            Injector<?> injector = Global.INJECTOR_INSTANCE_MAP.get(classes[i]);
            if (injector == null) {
                objects[i] = null;
            } else {
                objects[i] = injector.getInjectObj(ctx);
            }
        }
        return objects;
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
            log.info("Bot({}) connected.", bot.getQq());
        }
    }

    public static void removeBot(ChannelHandlerContext ctx) {
        getContextId(ctx).ifPresent(Global::removeBot);
    }

    public static void removeBot(String idText) {
        log.info("Bot({}) disconnected.", BOT.remove(idText).getQq());
    }

    private static Optional<String> getContextId(ChannelHandlerContext ctx) {
        return Optional.ofNullable(ctx)
                .map(ChannelHandlerContext::channel)
                .map(Channel::id)
                .map(ChannelId::asLongText);
    }

}
