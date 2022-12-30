package truekai.cc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ms_article")
public class MsArticleDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.INPUT)
      @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 评论数量
     */
    private Integer commentCounts;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 简介
     */
    private String summary;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览数量
     */
    private Integer viewCounts;

    /**
     * 是否置顶
     */
    private Integer weight;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 内容id
     */
    private Long bodyId;

    /**
     * 类别id
     */
    private Long categoryId;


}
