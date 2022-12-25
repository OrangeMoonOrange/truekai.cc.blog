package truekai.cc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import truekai.cc.model.MsArticleTagDO;
import truekai.cc.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
public interface MsArticleTagService extends IService<MsArticleTagDO> {


    /**
     * 首页最热标签
     * @return
     */
    Result hots();
}
