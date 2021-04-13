package org.kurenai.qqbot.domain;

import lombok.Data;
import org.kurenai.qqbot.constant.ResponseStatus;

import java.util.List;

/**
 * @author Kurenai
 * @since 2021-04-12 13:02
 */
@Data
public class GroupInfo {

    private List<Data>     data;
    private String         echo;
    private int            retcode;
    private ResponseStatus status;

    @lombok.Data
    public static class Data {
        private long   groupId;
        private String groupName;
        private int    memberCount;
        private int    maxMemberCount;
        private String echo;
    }
}
