package io.github.kurenairyu.qqbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liufuhong
 * @since 2021-04-02 14:24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BotInfo {

    private String xSelfId;
    private String xClientRole;

}
