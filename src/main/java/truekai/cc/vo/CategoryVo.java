package truekai.cc.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 02:23
 */
@Data
public class CategoryVo implements Serializable {
    private Long id;
    private String avatar;
    private String categoryName;
    private String description;
}
