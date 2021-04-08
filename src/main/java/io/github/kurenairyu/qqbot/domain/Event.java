package io.github.kurenairyu.qqbot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.kurenairyu.qqbot.constant.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author Kurenai
 * @since 2021-04-07 16:18
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event implements Serializable {

    private static final long                serialVersionUID = -3373312273943183272L;
    private              Long                time;
    @JsonIgnore
    @Setter(PRIVATE)
    private              LocalDateTime       localDateTime;
    private              Long                selfId;
    private              EventType           postType;
    private              EventMessageType    messageType;
    private              EventNoticeType     noticeType;
    private              EventSubMessageType subType;
    private              Integer             messageId;
    private              Long                userId;
    private              Long                groupId;
    private              Long                operatorId;
    private              List<Message>       message;
    private              String              rawMessage;
    private              Integer             font;
    private              Sender              sender;


    public LocalDateTime getLocalDateTime() {
        if (localDateTime == null) {
            localDateTime = Instant.ofEpochMilli(time * 1000L).atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }
        return localDateTime;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Sender {

        Long    userId;
        String  nickname;
        Sex     sex;
        Integer age;

    }
}
