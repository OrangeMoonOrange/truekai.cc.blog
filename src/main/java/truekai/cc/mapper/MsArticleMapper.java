package truekai.cc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import truekai.cc.interceptor.ArticleListRequest;
import truekai.cc.model.MsArticleDO;
import truekai.cc.vo.MsArticleVo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
public interface MsArticleMapper extends BaseMapper<MsArticleDO> {

    /**
     * 分页查询文章列表
     *
     * @param
     * @param articleListRequest
     * @return
     */
    List<MsArticleVo> articlesList(@Param("re") ArticleListRequest articleListRequest);

    /**
     * 根据id 查询文章详情
     * @param id
     * @return
     */
    MsArticleVo selectArticlesById(@Param("id") Long id);
}
