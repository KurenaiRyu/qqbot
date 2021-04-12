package org.kurenai.qqbot.domain;

import lombok.Data;

/**
 * @author Kurenai
 * @since 2021-04-12 13:02
 */
@Data
public class GroupInfo {
    private long   groupId;
    private String groupName;
    private int    memberCount;
    private int    maxMemberCount;
}
