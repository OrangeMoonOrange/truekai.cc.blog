package truekai.cc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import truekai.cc.model.MsCommentDO;
import truekai.cc.request.CommentRequest;
import truekai.cc.vo.Result;

public interface MsCommentService extends IService<MsCommentDO> {
    Result commentsByArticleId(Long id);

    Result comment(CommentRequest commentParam);
}
