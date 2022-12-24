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
}
