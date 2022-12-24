package truekai.cc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import truekai.cc.interceptor.ArticleListRequest;
import truekai.cc.model.MsArticleDO;
import truekai.cc.vo.MsArticleVo;

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
     * @param page
     * @param articleListRequest
     * @return
     */
    IPage<MsArticleVo> articlesList(Page page, @Param("re") ArticleListRequest articleListRequest);
}
