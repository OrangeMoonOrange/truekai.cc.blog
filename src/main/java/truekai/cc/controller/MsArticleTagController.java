package truekai.cc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import truekai.cc.service.MsArticleTagService;
import truekai.cc.vo.Result;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
@RestController
@RequestMapping("/tags")
public class MsArticleTagController {
    @Autowired
    private MsArticleTagService msArticleTagService;

    //首页最热标签
    @GetMapping("hot")
    public Result hot(){
        return msArticleTagService.hots();
    }
    @GetMapping("detail")
    public Result findAllDetail(){
        return msArticleTagService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return msArticleTagService.findDetailById(id);
    }

}

