package org.kurenai.qqbot.annotation.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.core.Global;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.util.JacksonFactory;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Kurenai
 * @since 2021-04-21 15:03
 */

public interface AnnotationHandler {

    void handle(Reflections reflections) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    default Action invokeMethod(Method method, Object instance, BotContext ctx) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        Object       o = method.invoke(instance, Global.getInjectObjects(ctx, method.getParameterTypes()));
        if (o == null) return null;
        if (o instanceof Action) {
            return (Action) o;
        } else if (o instanceof String) {
            return ctx.sendGroupMsg((String) o);
        } else {
            ObjectMapper mapper = JacksonFactory.getInstance();
            return ctx.sendGroupMsg(mapper.writeValueAsString(o));
        }
    }

}
