package truekai.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truekai.cc.mapper.MsCategoryMapper;
import truekai.cc.model.MsCategoryDO;
import truekai.cc.service.MsCategoryService;
import truekai.cc.vo.Result;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author truekai
 * @since 2022-12-25
 */
@Service
public class MsCategoryServiceImpl extends ServiceImpl<MsCategoryMapper, MsCategoryDO> implements MsCategoryService {

    @Autowired
    private MsCategoryMapper categoryMapper;

    @Override
    public Result findAllDetail() {
        List<MsCategoryDO> msCategoryDOS = categoryMapper.selectList(null);
        return Result.success(msCategoryDOS);
    }

    @Override
    public Result categoryDetailById(Long id) {
        MsCategoryDO msCategoryDO = categoryMapper.selectOne(new LambdaQueryWrapper<MsCategoryDO>()
                .eq(MsCategoryDO::getId, id));
        return Result.success(msCategoryDO);
    }

    @Override
    public Result findAll() {
        List<MsCategoryDO> msCategoryDOS = categoryMapper.selectList(null);
        return Result.success(msCategoryDOS);
    }
}
