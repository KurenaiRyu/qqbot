package org.kurenai.qqbot.util;

import lombok.NoArgsConstructor;
import org.kurenai.qqbot.constant.MessageType;
import org.kurenai.qqbot.pojo.Message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Kurenai
 * @since 2021-04-08 14:09
 */

@NoArgsConstructor
public class MsgChain extends ArrayList<Message> {

    public static MsgChain of(String text) {
        MsgChain msgChain = new MsgChain();
        msgChain.add(
                Message.builder()
                        .type(MessageType.TEXT)
                        .data(Message.Data.builder().text(text).build())
                        .build());
        return msgChain;
    }

    /**
     * create with at qq message
     * @param qq qq
     * @return MsgChain
     */
    public static MsgChain of(long qq) {
        MsgChain msgChain = new MsgChain();
        msgChain.add(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(qq + "").build())
                        .build());
        return msgChain;
    }

    public MsgChain at(long qq) {
        this.add(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(qq + "").build())
                        .build());
        return this;
    }

    public MsgChain atAll() {
        this.add(
                Message.builder()
                        .type(MessageType.AT)
                        .data(Message.Data.builder().qq(Message.Constant.ALL_QQ).build())
                        .build());
        return this;
    }

    public MsgChain text(String text) {
        this.add(
                Message.builder()
                        .type(MessageType.TEXT)
                        .data(Message.Data.builder().text(text).build())
                        .build());
        return this;
    }

    public MsgChain image(String file) {
        this.add(
                Message.builder()
                        .type(MessageType.IMAGE)
                        .data(Message.Data.builder().file(file).build())
                        .build());
        return this;
    }

    public MsgChain share(String url, String title) {
        this.add(
                Message.builder()
                        .type(MessageType.SHARE)
                        .data(Message.Data.builder().url(url).title(title).build())
                        .build());
        return this;
    }

    public MsgChain reply(int id) {
        this.add(
                Message.builder()
                        .type(MessageType.REPLY)
                        .data(Message.Data.builder().id(id).build())
                        .build());
        return this;
    }

    public MsgChain forward(int id) {
        this.add(
                Message.builder()
                        .type(MessageType.FORWARD)
                        .data(Message.Data.builder().id(id).build())
                        .build());
        return this;
    }

    public MsgChain location(float lat, float lon) {
        this.add(
                Message.builder()
                        .type(MessageType.LOCATION)
                        .data(Message.Data.builder().lat(lat).lon(lon).build())
                        .build()
        );
        return this;
    }


}
