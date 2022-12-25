package truekai.cc.request;

import lombok.Data;
import truekai.cc.vo.ArticleBodyVo;
import truekai.cc.vo.CategoryVo;
import truekai.cc.vo.TagsVo;

import java.util.List;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 17:02
 */
@Data
public class ArticleRequest {

    private Long id;

    private ArticleBodyVo body;

    private CategoryVo category;

    private String summary;

    private List<TagsVo> tags;

    private String title;

    private String search;
}
