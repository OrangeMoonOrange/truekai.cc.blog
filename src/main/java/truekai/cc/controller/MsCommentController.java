package truekai.cc.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import truekai.cc.vo.Result;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author truekai
 * @since 2022-12-25
 */
@RestController
@RequestMapping("/comments")
public class MsCommentController {


    //通过id或者评论
    @GetMapping("/article/{id}")
    public Result getArticleCommentById(@PathVariable("id") Long id) {


        return Result.success(null);
    }

}

