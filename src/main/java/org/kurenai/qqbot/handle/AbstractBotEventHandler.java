package org.kurenai.qqbot.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.BotContext;
import org.kurenai.qqbot.GlobalHolder;
import org.kurenai.qqbot.HandlerContext;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.util.JacksonFactory;

/**
 * @author Kurenai
 * @since 2021-04-08 14:50
 */
@Slf4j
public abstract class AbstractBotEventHandler implements BotEventHandler {

    protected final ObjectMapper mapper = JacksonFactory.getInstance();

    public abstract boolean match(BotContext ctx);

    @Override
    public String handle(BotContext ctx) {
        try {
            if (match(ctx)) {
                log.debug("Match {}", this.getClass().getSimpleName());
                Action action = doHandle(ctx);
                if (action != null) {
                    action.setEcho(GlobalHolder.id++ + "");
                    String json = mapper.writeValueAsString(action);
                    log.debug("Response {}", json);
                    return json;
                }
            }
        } catch (Exception exception) {
            String json = null;
            try {
                json = mapper.writeValueAsString(ctx.getEvent());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            log.error("{} can't handle {}", this.getClass().getSimpleName(), json);
            log.error(exception.getMessage(), exception);
        }
        return null;
    }

    public abstract Action doHandle(BotContext ctx);

    @Override
    public void handleResponse(HandlerContext ctx) {
    }
}
