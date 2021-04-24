package org.kurenai.qqbot.annotation.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.annotation.GroupEvent;
import org.kurenai.qqbot.constant.EventMessageType;
import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.core.Global;
import org.kurenai.qqbot.handle.AbstractEventHandler;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.Event;
import org.kurenai.qqbot.util.JacksonFactory;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Kurenai
 * @since 2021-04-21 15:02
 */

@Slf4j
public class GroupEventHandler implements AnnotationHandler {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    @Override
    public void handle(Reflections reflections) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Method> methods = reflections.getMethodsAnnotatedWith(GroupEvent.class);
        for (Method method : methods) {
            if (!method.isAnnotationPresent(GroupEvent.class)) continue;
            GroupEvent groupEvent = method.getAnnotation(GroupEvent.class);
            Object instance = Global.getClassInstance(method);



            AbstractEventHandler handler = new AbstractEventHandler() {
                @Override
                public boolean match(BotContext ctx) {
                    if (!ctx.matchType(EventMessageType.GROUP)) return false;
                    return (groupEvent.regex().isBlank() || ctx.regex(groupEvent.regex()))
                            && (groupEvent.command().length == 0 || ctx.matchCommand(groupEvent.ignoreCase(), groupEvent.command()));
                }

                @Override
                public Action doHandle(BotContext ctx, Event event) throws Exception {
                    return invokeMethod(method, instance, ctx);
                }
            };
            Global.HANDLERS.add(handler);
            log.debug("Load handler[{}]", method.getDeclaringClass().getSimpleName() + "#" + method.getName());
        }
    }
}
