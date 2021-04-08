package io.github.kurenairyu.qqbot;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.github.kurenairyu.qqbot.domain.Event;
import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class NettyWebSocketBootstrapTest {

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
    String msg = "{\"anonymous\":null,\"font\":0,\"group_id\":1035742830,\"message\":[{\"data\":{\"text\":\"正确答案是: 茜里(天使)\"},\"type\":\"text\"},{\"data\":{\"file\":\"965517f6475dd6bec004225fdfd1029a.image\",\"url\":\"http://gchat.qpic.cn/gchatpic_new/1209262091/1035742830-2373402240-965517F6475DD6BEC004225FDFD1029A/0?term=3\"},\"type\":\"image\"},{\"data\":{\"text\":\"\\n很遗憾，没有人答对~\"},\"type\":\"text\"}],\"message_id\":930993,\"message_seq\":930993,\"message_type\":\"group\",\"post_type\":\"message\",\"raw_message\":\"正确答案是: 茜里(天使)[CQ:image,file=965517f6475dd6bec004225fdfd1029a.image]\\n很遗憾，没有人答对~\",\"self_id\":929956850,\"sender\":{\"age\":0,\"area\":\"\",\"card\":\"\",\"level\":\"\",\"nickname\":\"咕噜灵波\",\"role\":\"member\",\"sex\":\"unknown\",\"title\":\"\",\"user_id\":1209262091},\"sub_type\":\"normal\",\"time\":1617784538,\"user_id\":1209262091}";
    ObjectMapper mapper = JsonMapper.builder()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .addModules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule())
        .build();
    Event event = mapper.readValue(msg, Event.class);
    System.out.println(event.getLocalDateTime());
    System.out.println(mapper.writeValueAsString(event));
  }
}