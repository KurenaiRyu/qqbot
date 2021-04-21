package org.kurenai.qqbot.util;

import lombok.NoArgsConstructor;
import org.kurenai.qqbot.constant.MessageType;
import org.kurenai.qqbot.pojo.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kurenai
 * @since 2021-04-08 14:09
 */

@NoArgsConstructor
public class MessageBuilder {

    private final List<Message> messages = new ArrayList<>();

    public static MessageBuilder newInstant() {
        return new MessageBuilder();
    }

    public static MessageBuilder of(String text) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.addMessage(
                Message.builder()
                        .type(MessageType.TEXT)
                        .data(Message.Data.builder().text(text).build())
                        .build());
        return messageBuilder;
    }

    public static MessageBuilder of(long qq) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.addMessage(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(qq + "").build())
                        .build());
        return messageBuilder;
    }

    public MessageBuilder at(long qq) {
        messages.add(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(qq + "").build())
                        .build());
        return this;
    }

    public MessageBuilder atAll() {
        messages.add(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(Message.Constant.ALL_QQ).build())
                        .build());
        return this;
    }

    public MessageBuilder text(String text) {
        addMessage(
                Message.builder()
                        .type(MessageType.TEXT)
                        .data(Message.Data.builder().text(text).build())
                        .build());
        return this;
    }

    public MessageBuilder image(String file) {
        messages.add(
                Message.builder()
                        .type(MessageType.IMAGE)
                        .data(Message.Data.builder().file(file).build())
                        .build());
        return this;
    }

    public MessageBuilder share(String url, String title) {
        messages.add(
                Message.builder()
                        .type(MessageType.SHARE)
                        .data(Message.Data.builder().url(url).title(title).build())
                        .build());
        return this;
    }

    public MessageBuilder reply(int id) {
        messages.add(
                Message.builder()
                        .type(MessageType.REPLY)
                        .data(Message.Data.builder().id(id).build())
                        .build());
        return this;
    }

    public MessageBuilder forward(int id) {
        messages.add(
                Message.builder()
                        .type(MessageType.FORWARD)
                        .data(Message.Data.builder().id(id).build())
                        .build());
        return this;
    }

    public MessageBuilder location(float lat, float lon) {
        messages.add(
                Message.builder()
                        .type(MessageType.LOCATION)
                        .data(Message.Data.builder().lat(lat).lon(lon).build())
                        .build()
        );
        return this;
    }

    public List<Message> build() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }


}
