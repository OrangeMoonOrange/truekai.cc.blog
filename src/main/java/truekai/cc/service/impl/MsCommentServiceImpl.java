package truekai.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import truekai.cc.interceptor.LoginInterceptor;
import truekai.cc.mapper.MsArticleMapper;
import truekai.cc.mapper.MsCommentMapper;
import truekai.cc.model.MsArticleDO;
import truekai.cc.model.MsCommentDO;
import truekai.cc.model.MsSysUserDO;
import truekai.cc.request.CommentRequest;
import truekai.cc.service.MsCommentService;
import truekai.cc.utils.CustomerId;
import truekai.cc.vo.CommentVo;
import truekai.cc.vo.Result;

import java.util.Date;
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

    @Autowired
    private MsArticleMapper articleMapper;

    @Override
    public Result commentsByArticleId(Long id) {
        //二级分类问题
        /**
         * 1. 根据文章id 查询 评论列表 从 comment 表中查询
         * 2. 根据作者的id 查询作者的信息
         * 3. 判断 如果 level = 1 要去查询它有没有子评论
         * 4. 如果有 根据评论id 进行查询 （parent_id）
         */
        List<CommentVo> comments = commentMapper.selectListById(id, null);
        //开始分类
        //找到所有一级分类
        List<CommentVo> msCommentDOStream = comments.stream().filter(comment -> {
                    return comment.getParentId().equals("0");
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
    @Transactional
    public Result comment(CommentRequest commentParam) {
        MsSysUserDO sysUserDO = LoginInterceptor.threadLocal.get();
        if (sysUserDO == null) {
            //默认给你一个用户：热心网友
            sysUserDO = new MsSysUserDO();
            sysUserDO.setId(1574784753217908745l);
        }
        //怎么可以动态知道他有没有登录呢？？
        MsCommentDO comment = new MsCommentDO();
        String l = customerId.nextId() + "";
        comment.setId(l);
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUserDO.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(new Date());
        String parent = commentParam.getParent();
        if (parent == null || parent.equals("0")) {
            comment.setLevel(1 + "");
        } else {
            comment.setLevel(2 + "");
        }
        comment.setParentId(parent == null ? "0" : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);

        //更新阅读数量
        LambdaUpdateWrapper<MsArticleDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MsArticleDO::getId, commentParam.getArticleId());
        wrapper.setSql(true, "comment_counts=comment_counts+1");
        articleMapper.update(null, wrapper);


        //更新文章的评论数量 ToDo
        List<CommentVo> comments = commentMapper.selectListById(commentParam.getArticleId(), l);
        return Result.success(comments.get(0));
    }
}
