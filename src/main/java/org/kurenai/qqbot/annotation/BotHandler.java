package org.kurenai.qqbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Kurenai
 * @since 2021-04-12 11:03
 */
@Target({ElementType.TYPE})
public @interface BotHandler {

    String value() default "";
}
