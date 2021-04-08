package io.github.kurenairyu.qqbot.util;

import io.github.kurenairyu.qqbot.constant.MessageType;
import io.github.kurenairyu.qqbot.domain.Message;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kurenai
 * @since 2021-04-08 14:09
 */

@NoArgsConstructor
public class CQCode {

    private final List<Message> messages = new ArrayList<>();

    public static CQCode newInstant() {
        return new CQCode();
    }

    public static CQCode of(String text) {
        CQCode cqCode = new CQCode();
        cqCode.addMessage(
                Message.builder()
                        .type(MessageType.TEXT)
                        .data(Message.Data.builder().text(text).build())
                        .build());
        return cqCode;
    }

    public static CQCode of(long qq) {
        CQCode cqCode = new CQCode();
        cqCode.addMessage(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(qq + "").build())
                        .build());
        return cqCode;
    }

    public CQCode at(long qq) {
        messages.add(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(qq + "").build())
                        .build());
        return this;
    }

    public CQCode atAll() {
        messages.add(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(Message.Constant.ALL_QQ).build())
                        .build());
        return this;
    }

    public CQCode text(String text) {
        addMessage(
                Message.builder()
                        .type(MessageType.TEXT)
                        .data(Message.Data.builder().text(text).build())
                        .build());
        return this;
    }

    public CQCode image(String file) {
        messages.add(
                Message.builder()
                        .type(MessageType.IMAGE)
                        .data(Message.Data.builder().file(file).build())
                        .build());
        return this;
    }

    public CQCode share(String url, String title) {
        messages.add(
                Message.builder()
                        .type(MessageType.SHARE)
                        .data(Message.Data.builder().url(url).title(title).build())
                        .build());
        return this;
    }

    public CQCode reply(int id) {
        messages.add(
                Message.builder()
                        .type(MessageType.REPLY)
                        .data(Message.Data.builder().id(id).build())
                        .build());
        return this;
    }

    public CQCode forward(int id) {
        messages.add(
                Message.builder()
                        .type(MessageType.FORWARD)
                        .data(Message.Data.builder().id(id).build())
                        .build());
        return this;
    }

    public List<Message> build() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }


}
