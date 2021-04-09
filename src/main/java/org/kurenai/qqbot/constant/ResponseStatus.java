package org.kurenai.qqbot.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Kurenai
 * @since 2021-04-09 15:32
 */
public enum ResponseStatus {
    @JsonProperty("ok")
    OK,
    @JsonProperty("failed")
    FAILED
}
