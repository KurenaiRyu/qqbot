package org.kurenai.qqbot.annotation;

import org.kurenai.qqbot.constant.*;

/**
 * @author Kurenai
 * @since 2021-04-12 11:06
 */
public @interface Event {

    EventType eventType() default EventType.NONE;

    EventMessageType eventMessageType() default EventMessageType.NONE;

    EventSubMessageType eventSubMessageType() default EventSubMessageType.NONE;

    EventNoticeType eventNoticeType() default EventNoticeType.NONE;

    MessageType messageType() default MessageType.NONE;

    String command() default "";

    String regex() default "";

    boolean ignoreCase() default false;

}
