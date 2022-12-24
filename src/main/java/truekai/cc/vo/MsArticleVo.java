package truekai.cc.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：熊凯凯
 * 日期：2022-12-24 21:15
 * 返回给前端的数据
 */

@Data
public class MsArticleVo implements Serializable {

    public MsArticleVo( ){

    }

    /**
     * 评论数量
     */
    private Integer commentCounts;
    private String tagId;

    /**
     * 创建时间
     */
    private Long createDate;

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
    private Long id;


    /**
     * 用户信息
     */
    private AuthorVo author;

    /**
     * 标签信息
     */
    private List<TagsVo> tags;

}
