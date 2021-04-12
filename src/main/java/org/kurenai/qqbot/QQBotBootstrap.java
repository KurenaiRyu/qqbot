package org.kurenai.qqbot;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.annotation.GroupEvent;
import org.kurenai.qqbot.constant.EventMessageType;
import org.kurenai.qqbot.domain.Action;
import org.kurenai.qqbot.domain.Event;
import org.kurenai.qqbot.handle.AbstractBotEventHandler;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Kurenai
 * @since 2021-04-02 13:42
 */

@Slf4j
public class QQBotBootstrap {

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        int port = 20010;
        start(port);
    }

    public static void start(int port, @NonNull String... scanPackages) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        scanPackages(scanPackages);
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            ChannelFuture channelFuture = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketChannelInitializer())
                    .bind(port).sync();

            channelFuture.channel().closeFuture().sync();

            log.info("Web socket server start at port {}.", port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static void scanPackages(String[] scanPackages) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SubTypesScanner subTypesScanner = new SubTypesScanner();
        TypeAnnotationsScanner typeAnnotationsScanner = new TypeAnnotationsScanner();
        MethodAnnotationsScanner methodAnnotationsScanner = new MethodAnnotationsScanner();
        for (String scanPackage : scanPackages) {
            Reflections reflections = new Reflections(scanPackage, subTypesScanner, typeAnnotationsScanner, methodAnnotationsScanner);
            Set<Method> methods = reflections.getMethodsAnnotatedWith(GroupEvent.class);
            for (Method method : methods) {
                GroupEvent groupEvent = method.getAnnotation(GroupEvent.class);
                String clazzName = method.getDeclaringClass().getName();
                Object instance = Global.CLASS_INSTANCE_MAP.get(clazzName);
                if (instance == null) {
                    instance = method.getDeclaringClass().getConstructor().newInstance();
                    Global.CLASS_INSTANCE_MAP.put(clazzName, instance);
                }
                Object finalInstance = instance;
                AbstractBotEventHandler handler = new AbstractBotEventHandler() {
                    @Override
                    public boolean match(BotContext ctx) {
                        if (!ctx.matchType(EventMessageType.GROUP)) return false;
                        if (groupEvent.regex().isBlank())
                            return groupEvent.command().length != 0 && ctx.matchCommand(groupEvent.ignoreCase(), groupEvent.command());
                        else
                            return ctx.regex(groupEvent.regex());
                    }

                    @Override
                    public Action doHandle(BotContext ctx, Event event) throws Exception {
                        return (Action) method.invoke(finalInstance, ctx, event);
                    }
                };
                Global.HANDLERS.add(handler);
            }
        }
    }
}
