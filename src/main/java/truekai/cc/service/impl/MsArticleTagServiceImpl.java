package truekai.cc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import truekai.cc.mapper.MsArticleTagMapper;
import truekai.cc.model.MsArticleTagDO;
import truekai.cc.service.MsArticleTagService;
import truekai.cc.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
@Service
public class MsArticleTagServiceImpl extends ServiceImpl<MsArticleTagMapper, MsArticleTagDO> implements MsArticleTagService {

    @Value("${hotTag.limit}")
    private Integer hotTaglimit;

    @Autowired
    private MsArticleTagMapper tagMapper;
    @Override
    public Result hots() {
        List<MsArticleTagDO> list=tagMapper.hots(hotTaglimit);
        return Result.success(list);
    }
}
