package org.kurenai.qqbot;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.kurenai.qqbot.annotation.handler.AnnotationHandler;
import org.kurenai.qqbot.core.Global;
import org.kurenai.qqbot.core.WebSocketChannelInitializer;
import org.kurenai.qqbot.exception.BotException;
import org.kurenai.qqbot.handle.EventHandler;
import org.kurenai.qqbot.injector.Injector;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Kurenai
 * @since 2021-04-02 13:42
 */

@Slf4j
public class QQBotBootstrap {

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, BotException {
        int port = 20010;
        start(port);
    }

    public static void start(int port) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, BotException {
        String scanPackage = deduceMainApplicationClass().getPackageName();
        String qqBotPackage = QQBotBootstrap.class.getPackageName();
        if (qqBotPackage.equals(scanPackage)) {
            start(port, qqBotPackage);
        } else {
            start(port, qqBotPackage, scanPackage);
        }
    }

    public static void start(int port, @NonNull String... scanPackages) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        scanAndInitHandler(scanPackages);
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            ChannelFuture channelFuture = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
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

    @SuppressWarnings("rawtypes")
    private static void scanAndInitHandler(String... scanPackages) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SubTypesScanner subTypesScanner = new SubTypesScanner();
        TypeAnnotationsScanner typeAnnotationsScanner = new TypeAnnotationsScanner();
        MethodAnnotationsScanner methodAnnotationsScanner = new MethodAnnotationsScanner();
        for (String scanPackage : scanPackages) {
            Reflections reflections = new Reflections(scanPackage, subTypesScanner, typeAnnotationsScanner, methodAnnotationsScanner);
            Set<Class<? extends Injector>> injectors = reflections.getSubTypesOf(Injector.class);
            for (Class<? extends Injector> clazz : injectors) {
                Injector injector = clazz.getConstructor().newInstance();
                Global.INJECTOR_INSTANCE_MAP.put(injector.getType(), injector);
            }

            Set<Class<? extends AnnotationHandler>> annotationHandlers = reflections.getSubTypesOf(AnnotationHandler.class);
            for (Class<? extends AnnotationHandler> handlerCls : annotationHandlers) {
                AnnotationHandler handler = handlerCls.getConstructor().newInstance();
                handler.handle(reflections);
            }
        }

        List<EventHandler> handlers =
                Global.HANDLERS.stream().dropWhile(Objects::isNull).sorted(Comparator.comparing(EventHandler::order)).collect(Collectors.toList());
        Global.HANDLERS.clear();
        Global.HANDLERS.addAll(handlers);
    }

    /**
     * 获取程序入口类
     */
    private static Class<?> deduceMainApplicationClass() throws BotException {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new BotException("Can not found main application class!");
        }
        throw new BotException("Can not found main application class!");
    }
}
