package org.kurenai.qqbot.annotation.handler;

import org.kurenai.qqbot.annotation.PrivateEvent;
import org.kurenai.qqbot.constant.EventMessageType;
import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.core.Global;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.Event;
import org.kurenai.qqbot.handle.AbstractEventHandler;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Kurenai
 * @since 2021-04-21 15:02
 */

public class PrivateEventHandler implements AnnotationHandler {

    @Override
    public void handle(Reflections reflections) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Method> methods = reflections.getMethodsAnnotatedWith(PrivateEvent.class);
        for (Method method : methods) {
            if (!method.isAnnotationPresent(PrivateEvent.class)) continue;
            PrivateEvent privateEvent = method.getAnnotation(PrivateEvent.class);
            Object instance = Global.getClassInstance(method);
            AbstractEventHandler handler = new AbstractEventHandler() {
                @Override
                public boolean match(BotContext ctx) {
                    if (!ctx.matchType(EventMessageType.PRIVATE)) return false;
                    return (privateEvent.regex().isBlank() || ctx.regex(privateEvent.regex()))
                            && (privateEvent.command().length == 0 || ctx.matchCommand(privateEvent.ignoreCase(), privateEvent.command()));
                }

                @Override
                public Action doHandle(BotContext ctx, Event event) throws Exception {
                    return invokeMethod(method, instance, ctx);
                }
            };
            Global.HANDLERS.add(handler);
        }
    }
}
