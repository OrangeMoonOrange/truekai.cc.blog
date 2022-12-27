package truekai.cc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import truekai.cc.request.CommentRequest;
import truekai.cc.service.MsCommentService;
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

    @Autowired
    private MsCommentService commentsService;


    //查询评论列表
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id) {
        return commentsService.commentsByArticleId(id);
    }


    @PostMapping("create/change")
    public Result comment(@RequestBody CommentRequest commentParam) {
        return commentsService.comment(commentParam);
    }

}

