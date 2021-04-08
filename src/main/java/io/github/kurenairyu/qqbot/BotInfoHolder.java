package io.github.kurenairyu.qqbot;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liufuhong
 * @since 2021-04-02 14:21
 */

@Getter
@Setter
@Slf4j
public class BotInfoHolder {

    private static final Map<String, BotInfo> POOL = new HashMap<>();
    private static final BotInfo EMPTY_BOT_INFO = new BotInfo();

    private BotInfoHolder() {

    }

    public static BotInfo get(ChannelHandlerContext ctx) {
        return getContextId(ctx)
                .map(POOL::get)
                .orElse(EMPTY_BOT_INFO);
    }

    public static void put(ChannelHandlerContext ctx, BotInfo botInfo) {
        getContextId(ctx).ifPresent(id -> put(id, botInfo));
    }

    public static void put(String idText, BotInfo botInfo) {
        if (botInfo != null && StringUtils.isNotBlank(idText)) {
            POOL.put(idText, botInfo);
            log.info("Put {}", botInfo);
        }
    }

    public static void remove(ChannelHandlerContext ctx) {
        getContextId(ctx).ifPresent(BotInfoHolder::remove);
    }

    public static void remove(String idText) {
        log.info("Remove {}", POOL.remove(idText));
    }

    private static Optional<String> getContextId(ChannelHandlerContext ctx) {
        return Optional.ofNullable(ctx)
                .map(ChannelHandlerContext::channel)
                .map(Channel::id)
                .map(ChannelId::asLongText);
    }

}
