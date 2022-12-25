package truekai.cc.service;

import truekai.cc.interceptor.ArticleListRequest;
import truekai.cc.model.MsArticleDO;
import com.baomidou.mybatisplus.extension.service.IService;
import truekai.cc.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
public interface MsArticleService extends IService<MsArticleDO> {

    /**
     * 文章列表
     * @param articleListRequest
     * @return
     */
    Result articlesList(ArticleListRequest articleListRequest);

    /**
     * 文章详情查询
     * @param id
     * @return
     */
    Result articlesViewById(Long id);

    /**
     * 首页最热文章
     * @return
     */
    Result hotArticles();

    /**
     * 首页最新文章
     * @return
     */
    Result newArticles();
}
