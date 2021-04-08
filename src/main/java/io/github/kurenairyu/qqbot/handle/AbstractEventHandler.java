package io.github.kurenairyu.qqbot.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kurenairyu.qqbot.domain.Event;
import io.github.kurenairyu.qqbot.util.JacksonFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kurenai
 * @since 2021-04-08 14:50
 */
@Slf4j
public abstract class AbstractEventHandler implements EventHandler {

    protected final ObjectMapper mapper = JacksonFactory.getInstance();

    public abstract boolean match(Event event);

    @Override
    public String handle(Event event) {
        try {
            if (match(event)) {
                log.info("Match {}", this.getClass().getSimpleName());
                String json = mapper.writeValueAsString(doHandle(event));
                log.debug("Response {}", json);
                return json;
            }
        } catch (Exception exception) {
            String json = null;
            try {
                json = mapper.writeValueAsString(event);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            log.error("{} can't handle {}", this.getClass().getSimpleName(), json);
            log.error(exception.getMessage(), exception);
        }
        return null;
    }

    public abstract Object doHandle(Event event);
}
