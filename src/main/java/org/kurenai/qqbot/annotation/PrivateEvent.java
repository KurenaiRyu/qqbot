package org.kurenai.qqbot.annotation;

/**
 * @author Kurenai
 * @since 2021-04-12 11:06
 */
public @interface PrivateEvent {

    String command() default "";

    String regex() default "";

    boolean ignoreCase() default false;

}
