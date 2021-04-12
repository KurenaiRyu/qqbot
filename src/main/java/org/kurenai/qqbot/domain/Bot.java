package org.kurenai.qqbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kurenai
 * @since 2021-04-02 14:24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bot {

    private String xSelfId;
    private String xClientRole;

}
