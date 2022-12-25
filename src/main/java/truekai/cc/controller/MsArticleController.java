package truekai.cc.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import truekai.cc.interceptor.ArticleListRequest;
import truekai.cc.request.ArticleRequest;
import truekai.cc.service.MsArticleService;
import truekai.cc.vo.Result;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
@RestController
@Slf4j
public class MsArticleController {

    @Autowired
    private MsArticleService msArticleService;

    //文章列表
    @PostMapping("articles")
    public Result articles(@RequestBody ArticleListRequest articleListRequest) {
        log.info("文章列表入参："+articleListRequest);
        Result result = msArticleService.articlesList(articleListRequest);
        return result;
    }

    @PostMapping("articles/view/{id}")
    public Result articlesViewById(@PathVariable("id") Long id) {
        Result result = msArticleService.articlesViewById(id);
        return result;
    }

    //首页最热文章
    @PostMapping("articles/hot")
    public Result hotArticles() {
        Result result = msArticleService.hotArticles();
        return result;
    }
    @PostMapping("articles/new")
    public Result newArticles() {
        Result result = msArticleService.newArticles();
        return result;
    }

    //文章归档
    @PostMapping("/articles/listArchives")
    public Result listArchives() {
        Result result = msArticleService.listArchives();
        return result;
    }

    //文章发布
    @PostMapping("articles/publish")
    public Result publish(@RequestBody ArticleRequest articleParam){
        return msArticleService.publish(articleParam);
    }

    @PostMapping("articles/{id}")
    public Result articleById(@PathVariable("id") Long id){
        Result result = msArticleService.articlesViewById(id);
        return result;
    }



}

