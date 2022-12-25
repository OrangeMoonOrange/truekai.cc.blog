package truekai.cc.service;

import truekai.cc.model.MsCategoryDO;
import com.baomidou.mybatisplus.extension.service.IService;
import truekai.cc.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author truekai
 * @since 2022-12-25
 */
public interface MsCategoryService extends IService<MsCategoryDO> {

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
