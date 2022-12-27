package truekai.cc.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * 作者：熊凯凯
 * 日期：2022-12-27 15:15
 */
@Data
public class CommentVo {
    //防止前端 精度损失 把id转为string
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private AuthorVo author;

    private String content;

    @TableField(exist = false)
    private List<CommentVo> childrens;

    private Long createDate;

    private Integer level;

    private AuthorVo toUser;

    private String parentId;
}
