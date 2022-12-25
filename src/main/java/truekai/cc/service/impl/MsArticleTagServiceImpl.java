package truekai.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import truekai.cc.mapper.MsArticleTagMapper;
import truekai.cc.mapper.MsTagMapper;
import truekai.cc.model.MsArticleTagDO;
import truekai.cc.model.MsTagDO;
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

    @Autowired
    private MsTagMapper msTagMapper;
    @Override
    public Result hots() {
        List<MsArticleTagDO> list=tagMapper.hots(hotTaglimit);
        return Result.success(list);
    }

    @Override
    public Result findAllDetail() {
        List<MsTagDO> list = msTagMapper.selectList(null);
        return Result.success(list);
    }

    @Override
    public Result findDetailById(Long id) {
        MsTagDO msTagDO = msTagMapper.selectOne(new LambdaQueryWrapper<MsTagDO>()
                .eq(MsTagDO::getId,id));
        return Result.success(msTagDO);
    }
}
