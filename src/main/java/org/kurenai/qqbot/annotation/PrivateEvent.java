package org.kurenai.qqbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kurenai
 * @since 2021-04-12 11:06
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivateEvent {

    String[] command() default {};

    String regex() default "";

    boolean ignoreCase() default false;

}
