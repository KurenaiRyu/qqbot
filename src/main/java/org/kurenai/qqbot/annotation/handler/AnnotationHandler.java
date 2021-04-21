package org.kurenai.qqbot.annotation.handler;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Kurenai
 * @since 2021-04-21 15:03
 */

public interface AnnotationHandler {

    void handle(Reflections reflections) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

}
