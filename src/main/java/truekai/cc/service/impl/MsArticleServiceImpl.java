package truekai.cc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truekai.cc.interceptor.ArticleListRequest;
import truekai.cc.mapper.MsArticleMapper;
import truekai.cc.model.MsArticleDO;
import truekai.cc.service.MsArticleService;
import truekai.cc.utils.PageVo;
import truekai.cc.utils.pageList;
import truekai.cc.vo.Result;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
@Service
@Slf4j
public class MsArticleServiceImpl extends ServiceImpl<MsArticleMapper, MsArticleDO> implements MsArticleService {
    @Autowired
    private MsArticleMapper articleMapper;

    @Override
    public Result articlesList(ArticleListRequest articleListRequest) {
        log.info("MsArticleServiceImpl.articlesList入参：{}", articleListRequest);
        List list = articleMapper.articlesList( articleListRequest);
        PageVo<Object> objectPageVo = pageList.pageList(list, articleListRequest.getPage(), articleListRequest.getPageSize());
        //增加分页参数给前端
        return Result.success(objectPageVo.getList());
    }
}
