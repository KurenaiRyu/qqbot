package org.kurenai.qqbot.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.core.Global;
import org.kurenai.qqbot.pojo.Action;
import org.kurenai.qqbot.pojo.Event;
import org.kurenai.qqbot.util.JacksonFactory;

/**
 * @author Kurenai
 * @since 2021-04-08 14:50
 */
@Slf4j
public abstract class AbstractEventHandler implements EventHandler {

    protected final ObjectMapper mapper = JacksonFactory.getInstance();

    public abstract boolean match(BotContext ctx);

    @Override
    public String handle(BotContext ctx) {
        try {
            if (match(ctx)) {
                log.debug("Match {}", this.getClass().getSimpleName());
                Action action = doHandle(ctx, ctx.getEvent());
                if (action != null) {
                    ctx.setEventHandler(this);
                    if (isHandleResponse()) {
                        String id = String.valueOf(Global.id++);
                        action.setEcho(id);
                        Global.BOT_CONTEXT_MAP.put(id, ctx);
                    }
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
            log.error("{} can't handle {}.", this.getClass().getSimpleName(), json);
            log.error(exception.getMessage(), exception);
        }
        return null;
    }

    public abstract Action doHandle(BotContext ctx, Event event) throws Exception;

    @Override
    public void onResponse(BotContext ctx) {
        log.debug("onResponse: {}", ctx.getResponse());
    }

    @Override
    public boolean isContinue() {
        return true;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean isHandleResponse() {
        return false;
    }
}
