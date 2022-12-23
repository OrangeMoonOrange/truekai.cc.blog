package truekai.cc.service;

import truekai.cc.model.MsSysUserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import truekai.cc.request.LoginRequest;
import truekai.cc.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author truekai
 * @since 2022-12-23
 */
public interface MsSysUserService extends IService<MsSysUserDO> {

    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    Result login(LoginRequest loginRequest);

    /**
     * 用户注册
     * @param loginRequest
     * @return
     */
    Result register(LoginRequest loginRequest);

    /**
     * 用户登出
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 校验token
     * @param token
     * @return
     */
    MsSysUserDO checkToken(String token);
}
