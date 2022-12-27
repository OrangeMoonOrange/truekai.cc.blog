package truekai.cc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truekai.cc.mapper.MsCommentMapper;
import truekai.cc.model.MsCommentDO;
import truekai.cc.request.CommentRequest;
import truekai.cc.service.MsCommentService;
import truekai.cc.utils.CustomerId;
import truekai.cc.vo.CommentVo;
import truekai.cc.vo.Result;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：熊凯凯
 * 日期：2022-12-27 13:49
 */
@Service
public class MsCommentServiceImpl extends ServiceImpl<MsCommentMapper, MsCommentDO> implements MsCommentService {


    @Autowired
    private MsCommentMapper commentMapper;
    @Autowired
    private CustomerId customerId;

    @Override
    public Result commentsByArticleId(Long id) {
        //二级分类问题
        /**
         * 1. 根据文章id 查询 评论列表 从 comment 表中查询
         * 2. 根据作者的id 查询作者的信息
         * 3. 判断 如果 level = 1 要去查询它有没有子评论
         * 4. 如果有 根据评论id 进行查询 （parent_id）
         */
        List<CommentVo> comments = commentMapper.selectListById(id,null);
        //开始分类
        //找到所有一级分类
        List<CommentVo> msCommentDOStream = comments.stream().filter(comment -> {
                    return comment.getParentId() == 0;
                }).map(comment -> {
                    comment.setChildrens(getCommentChinds(comment, comments));
                    return comment;
                })
                .collect(Collectors.toList());
        return Result.success(msCommentDOStream);
    }

    /**
     * 查找该大类下所有的小雷
     *
     * @param comment  某个大类
     * @param comments 所有的类别数据
     * @return
     */
    private List<CommentVo> getCommentChinds(CommentVo comment, List<CommentVo> comments) {
        return comments.stream().filter(com -> {
            //根据大类的父编号找到他的直属小类
            return com.getParentId().equals(comment.getId());
        }).map(com -> {
            //根据小类递归找到对应的小小类
            com.setChildrens(getCommentChinds(com, comments));
            return com;
        }).collect(Collectors.toList());
    }

    @Override
    public Result comment(CommentRequest commentParam) {
        MsCommentDO comment = new MsCommentDO();
        long l = customerId.nextId();
        comment.setId(l);
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(1L);//TODO
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1 + "");
        } else {
            comment.setLevel(2 + "");
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);
        //更新文章的评论数量 ToDo
        System.out.println("dddd");
        List<CommentVo> comments = commentMapper.selectListById(commentParam.getArticleId(),l);
        return Result.success(comments.get(0));
    }
}
