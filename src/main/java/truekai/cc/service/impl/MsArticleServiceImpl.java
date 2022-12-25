package truekai.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import truekai.cc.interceptor.ArticleListRequest;
import truekai.cc.mapper.MsArticleMapper;
import truekai.cc.model.MsArticleDO;
import truekai.cc.service.MsArticleService;
import truekai.cc.service.asyncService.ThreadService;
import truekai.cc.utils.PageVo;
import truekai.cc.utils.pageList;
import truekai.cc.vo.ArchivesVo;
import truekai.cc.vo.MsArticleVo;
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

    @Autowired
    private ThreadService threadService;


    @Value("${hotArticles.limit}")
    private Integer hotArticleslimit;

    @Value("${newArticles.limit}")
    private Integer newArticleslimit;

    @Override
    public Result articlesList(ArticleListRequest articleListRequest) {
        log.info("MsArticleServiceImpl.articlesList入参：{}", articleListRequest);
        List list = articleMapper.articlesList(articleListRequest);
        PageVo<Object> objectPageVo = pageList.pageList(list, articleListRequest.getPage(), articleListRequest.getPageSize());
        //todo 增加分页参数给前端

        return Result.success(objectPageVo.getList());
    }

    @Override
    public Result articlesViewById(Long id) {
        log.info("MsArticleServiceImpl.articlesViewById：{}", id);
        MsArticleVo msArticleVo = articleMapper.selectArticlesById(id);
        //异步更新阅读的时候如何保证高并发的时候阅读数量是正常的?
        threadService.updateArticleViewCount(id, msArticleVo);
        return Result.success(msArticleVo);
    }

    @Override
    public Result hotArticles() {
        List<MsArticleDO> msArticleDOS = articleMapper.selectList(new LambdaQueryWrapper<MsArticleDO>()
                .select(MsArticleDO::getId, MsArticleDO::getTitle)
                .orderByDesc(MsArticleDO::getViewCounts)
                .last("limit " + hotArticleslimit));
        return Result.success(msArticleDOS);
    }

    @Override
    public Result newArticles() {
        List<MsArticleDO> msArticleDOS = articleMapper.selectList(new LambdaQueryWrapper<MsArticleDO>()
                .select(MsArticleDO::getId, MsArticleDO::getTitle)
                .orderByDesc(MsArticleDO::getCreateDate)
                .last("limit " + newArticleslimit));
        return Result.success(msArticleDOS);
    }

    @Override
    public Result listArchives() {
        List<ArchivesVo> list=articleMapper.listArchives();
        return Result.success(list);
    }
}
