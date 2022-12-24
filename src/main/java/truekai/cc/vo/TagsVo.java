package truekai.cc.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：熊凯凯
 * 日期：2022-12-24 21:38
 */
@Data
public class TagsVo implements Serializable {

    private Long id;
    private String tagName;

    private String avatar;
}
