package org.kurenai.qqbot.pojo.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kurenai.qqbot.constant.ResponseStatus;

import java.util.Collection;
import java.util.List;

/**
 * @author Kurenai
 * @since 2021-04-12 13:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupInfo extends BaseResult {

    private Collection<Data> data;

    @lombok.Data
    public static class Data {
        private long groupId;
        private String groupName;
        private long groupCreateTime;
        private int memberCount;
        private int maxMemberCount;
    }
}
