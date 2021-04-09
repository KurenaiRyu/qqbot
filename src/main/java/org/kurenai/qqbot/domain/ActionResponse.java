package org.kurenai.qqbot.domain;

import lombok.Data;
import org.kurenai.qqbot.constant.ResponseStatus;

/**
 * @author Kurenai
 * @since 2021-04-09 15:30
 */
@Data
public class ActionResponse {

    private Data           data;
    private String         echo;
    private int            retcode;
    private ResponseStatus status;

    @lombok.Data
    public static class Data {
        private int messageId;
    }

}
