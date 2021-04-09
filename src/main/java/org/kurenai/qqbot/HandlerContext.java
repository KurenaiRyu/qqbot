package org.kurenai.qqbot;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kurenai.qqbot.domain.ActionResponse;
import org.kurenai.qqbot.handle.BotEventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kurenai
 * @since 2021-04-09 15:45
 */
@Data
@AllArgsConstructor
public class HandlerContext {

    private final Map<String, Object> context = new HashMap<>();

    private final BotContext      botContext;
    private final BotEventHandler eventHandler;
    private       ActionResponse  response;

    public HandlerContext(BotContext botContext, BotEventHandler eventHandler) {
        this.botContext = botContext;
        this.eventHandler = eventHandler;
    }

    public static HandlerContext of(BotContext botContext, BotEventHandler handler) {
        return new HandlerContext(botContext, handler);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) context.get(key);
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }
}
