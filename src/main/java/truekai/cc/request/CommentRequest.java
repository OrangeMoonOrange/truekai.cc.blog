package truekai.cc.request;

import lombok.Data;

/**
 * 作者：熊凯凯
 * 日期：2022-12-27 13:51
 */
@Data
public class CommentRequest {
    private Long articleId;

    private String content;

    private String parent;

    private Long toUserId;
}
