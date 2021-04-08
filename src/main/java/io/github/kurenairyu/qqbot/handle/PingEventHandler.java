package io.github.kurenairyu.qqbot.handle;

import io.github.kurenairyu.qqbot.domain.Action;
import io.github.kurenairyu.qqbot.domain.Event;
import io.github.kurenairyu.qqbot.domain.Message;
import io.github.kurenairyu.qqbot.util.CQCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Kurenai
 * @since 2021-04-08 14:57
 */

@Slf4j
public class PingEventHandler extends AbstractEventHandler {
    @Override
    public boolean match(Event event) {
        List<Message> message = event.getMessage();
        if (message != null && !message.isEmpty()) {
            return message.stream()
                    .filter(Objects::nonNull)
                    .anyMatch(msg -> Optional.ofNullable(msg.getData())
                            .map(Message.Data::getText)
                            .map("ping"::equals)
                            .orElse(false));
        }
        return false;
    }

    @Override
    public Object doHandle(Event event) {
        return Action.builder()
                .action(Action.SEND_MSG)
                .params(Action.Param.builder()
                                .userId(event.getGroupId() == null ? event.getUserId() : null)
                                .groupId(event.getGroupId())
                                .message(CQCode.of(event.getUserId())
                                                 .text("Pong!")
                                                 .build())
                                .build())
                .build()
                .async();
    }
}
