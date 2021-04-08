package io.github.kurenairyu.qqbot.domain;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.kurenairyu.qqbot.enums.EventType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author liufuhong
 * @since 2021-04-07 16:18
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  private long time;
  @JsonIgnore
  @Setter(PRIVATE)
  private LocalDateTime localDateTime;
  private long selfId;
  private EventType postType;

  public LocalDateTime getLocalDateTime() {
    if (localDateTime == null) {
      localDateTime = Instant.ofEpochMilli(time * 1000L).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    return localDateTime;
  }
}
