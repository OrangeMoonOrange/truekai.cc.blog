package truekai.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import truekai.cc.interceptor.ArticleListRequest;
import truekai.cc.interceptor.LoginInterceptor;
import truekai.cc.mapper.MsArticleBodyMapper;
import truekai.cc.mapper.MsArticleMapper;
import truekai.cc.mapper.MsArticleTagMapper;
import truekai.cc.model.MsArticleBodyDO;
import truekai.cc.model.MsArticleDO;
import truekai.cc.model.MsArticleTagDO;
import truekai.cc.model.MsSysUserDO;
import truekai.cc.request.ArticleRequest;
import truekai.cc.service.MsArticleService;
import truekai.cc.service.asyncService.ThreadService;
import truekai.cc.utils.CustomerId;
import truekai.cc.utils.PageVo;
import truekai.cc.utils.pageList;
import truekai.cc.vo.ArchivesVo;
import truekai.cc.vo.MsArticleVo;
import truekai.cc.vo.Result;
import truekai.cc.vo.TagsVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CustomerId customerId;

    @Autowired
    private MsArticleTagMapper msArticleTagMapper;

    @Autowired
    private MsArticleBodyMapper msArticleBodyMapper;


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
        threadService.updateArticleViewCount2(id, msArticleVo);
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
        List<ArchivesVo> list = articleMapper.listArchives();
        return Result.success(list);
    }

    @Override
    @Transactional
    public Result publish(ArticleRequest articleParam) {
        MsSysUserDO sysUserDO = LoginInterceptor.threadLocal.get();
        MsArticleDO articleDO = null;
        String id = "";
        if (articleParam.getId() == null) { //新增
            articleDO = new MsArticleDO();
            //维护article表信息
            //生成主键
            long articleDOKey = customerId.nextId();
            long articleBodyKey = customerId.nextId();

            articleDO.setId(articleDOKey);
            articleDO.setAuthorId(sysUserDO.getId());
            articleDO.setTitle(articleParam.getTitle());
            articleDO.setViewCounts(0);
            articleDO.setWeight(0);
            articleDO.setSummary(articleParam.getSummary());
            articleDO.setCommentCounts(0);
            articleDO.setCreateDate(System.currentTimeMillis());
            articleDO.setCategoryId(articleParam.getCategory().getId());
            articleDO.setBodyId(articleBodyKey);

            //维护Article_body 信息

            MsArticleBodyDO bodyDO = new MsArticleBodyDO();
            bodyDO.setArticleId(articleDO.getId());
            bodyDO.setContent(articleParam.getBody().getContent());
            bodyDO.setContentHtml(articleParam.getBody().getContentHtml());
            bodyDO.setId(articleBodyKey);
            msArticleBodyMapper.insert(bodyDO);

            //维护Article_tag信息
            MsArticleTagDO articleTagDO = null;
            for (TagsVo tag : articleParam.getTags()) {
                articleTagDO = new MsArticleTagDO();
                long articleTagDOKey = customerId.nextId();
                articleTagDO.setArticleId(articleDOKey);
                articleTagDO.setTagId(tag.getId());
                articleTagDO.setId(articleTagDOKey);
                msArticleTagMapper.insert(articleTagDO);
            }
            articleMapper.insert(articleDO);
            id = articleDOKey + "";
        } else {//修改
            //更新新的信息
            articleDO = new MsArticleDO();
            articleDO.setTitle(articleParam.getTitle());
            articleDO.setSummary(articleParam.getSummary());
            articleDO.setCategoryId(articleParam.getCategory().getId());
            articleDO.setId(articleParam.getId());
            articleMapper.updateById(articleDO);

            //维护Article_body 更新
            MsArticleBodyDO bodyDO = new MsArticleBodyDO();
            bodyDO.setContent(articleParam.getBody().getContent());
            bodyDO.setContentHtml(articleParam.getBody().getContentHtml());
            msArticleBodyMapper.update(bodyDO, new LambdaUpdateWrapper<MsArticleBodyDO>()
                    .eq(MsArticleBodyDO::getArticleId, articleParam.getId()));

            //维护Article_tag信息 先删除再更新
            msArticleTagMapper.delete(new LambdaQueryWrapper<MsArticleTagDO>()
                    .eq(MsArticleTagDO::getArticleId, articleParam.getId()));
            MsArticleTagDO articleTagDO = null;
            for (TagsVo tag : articleParam.getTags()) {
                articleTagDO = new MsArticleTagDO();
                long articleTagDOKey = customerId.nextId();
                articleTagDO.setArticleId(articleParam.getId());
                articleTagDO.setTagId(tag.getId());
                articleTagDO.setId(articleTagDOKey);
                msArticleTagMapper.insert(articleTagDO);
            }
            id = articleParam.getId() + "";
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        return Result.success(map);
    }

    @Override
    public Result flush() {
        MsSysUserDO sysUserDO = LoginInterceptor.threadLocal.get();
        //删除所有数据
        articleMapper.delete(null);
        msArticleTagMapper.delete(null);
        msArticleBodyMapper.delete(null);
        return null;
    }
}
