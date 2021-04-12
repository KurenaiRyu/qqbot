package org.kurenai.qqbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kurenai.qqbot.annotation.GroupEvent;
import org.kurenai.qqbot.domain.Event;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@Slf4j
class QQBotBootstrapTest {

    @Test
    void logTest() {
        log.info("info");
        log.error("error");
        log.warn("warn");
        log.debug("debug");
        log.trace("trace");
        System.out.println("test");
    }

    @Test
    void jacksonTest() throws JsonProcessingException {
        String msg = "{\"anonymous\":null,\"font\":0,\"group_id\":213690137,\"message\":[{\"data\":{\"text\":\"所以这床不可能只有一米八\"},\"type\":\"text\"}],\"message_id\":-620787640,\"message_seq\":119464,\"message_type\":\"group\",\"post_type\":\"message\",\"raw_message\":\"所以这床不可能只有一米八\",\"self_id\":929956850,\"sender\":{\"age\":0,\"area\":\"\",\"card\":\"\",\"level\":\"\",\"nickname\":\"墨染长空✨\",\"role\":\"admin\",\"sex\":\"unknown\",\"title\":\"\",\"user_id\":1075706923},\"sub_type\":\"normal\",\"time\":1617851920,\"user_id\":1075706923}";
        ObjectMapper mapper = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .addModules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule())
                .build();
        Event event = mapper.readValue(msg, Event.class);
        System.out.println(event.getLocalDateTime());
        System.out.println(mapper.writeValueAsString(event));
    }

    @Test
    void testAnno() {
        HashMap<String, Object> map = new HashMap<>();

        Reflections reflections = new Reflections("org.kurenai.qqbot",
                                                  new MethodAnnotationsScanner(),
                                                  new TypeAnnotationsScanner(),
                                                  new SubTypesScanner());

        for (Method method : reflections.getMethodsAnnotatedWith(GroupEvent.class)) {
            System.out.println(method.getName());
            String clazzName = method.getDeclaringClass().getName();
            Object instance = map.get(clazzName);
            try {
                if (instance == null) {
                    instance = method.getDeclaringClass().getConstructor().newInstance();
                    map.put(clazzName, instance);
                }
                method.invoke(instance, new BotContext(), new Event());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                log.error("调用方法失败", e);
            }
        }
    }
}