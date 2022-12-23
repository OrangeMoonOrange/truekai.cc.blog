package truekai.cc.mapper;

import org.apache.ibatis.annotations.Param;
import truekai.cc.model.MsSysUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author truekai
 * @since 2022-12-23
 */
public interface MsSysUserMapper extends BaseMapper<MsSysUserDO> {

    /**
     *
     * @param account
     * @param password
     * @return
     */
    MsSysUserDO findUser(@Param("account") String account, @Param("password") String password);
}
