package org.kurenai.qqbot.pojo.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Kurenai
 * @since 2021-04-12 13:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FriendInfo extends BaseResult {

    private List<Data> data;

    @lombok.Data
    public static class Data {
        private long userId;
        private String nickname;
        private String remark;
    }
}
