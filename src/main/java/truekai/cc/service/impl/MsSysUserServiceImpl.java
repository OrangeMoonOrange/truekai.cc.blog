package truekai.cc.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.transaction.annotation.Transactional;
import truekai.cc.enumCode.ErrorCode;
import truekai.cc.interceptor.LoginInterceptor;
import truekai.cc.model.MsSysUserDO;
import truekai.cc.mapper.MsSysUserMapper;
import truekai.cc.request.LoginRequest;
import truekai.cc.service.MsSysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import truekai.cc.utils.JWTUtils;
import truekai.cc.vo.Result;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author truekai
 * @since 2022-12-23
 */
@Service
@Slf4j
public class MsSysUserServiceImpl extends ServiceImpl<MsSysUserMapper, MsSysUserDO> implements MsSysUserService {

    //密码加的盐
    private static final String slat = "mszlu!@#";

    @Autowired
    private MsSysUserMapper userMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public Result login(LoginRequest loginRequest) {
        log.info("MsSysUserServiceImpl.login入参：{}", loginRequest);
        /**
         * 1.校验入参是否合法
         * 2.查询用户表看用户是否存在
         * 2.1.不存在，登录失败
         * 3.存在用户，生成jwt 返回给前端
         * 4.jwt放入redis ,用于登录认证
         */
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();
        if (StringUtils.isAnyBlank(account, password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + slat);
        MsSysUserDO userDO = userMapper.findUser(account, password);
        if (userDO == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(userDO.getId());

        redisTemplate.opsForValue().set("user::login::" + token, JSON.toJSONString(userDO), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(LoginRequest loginRequest) {
        log.info("MsSysUserServiceImpl.register入参：{}", loginRequest);
        /**
         * 1.校验入参是否合法
         * 2.查询用户表看用户是否存在
         * 2.1.存在，返回重复注册
         * 3.生成jwt 返回给前端
         * 4.jwt放入redis ,用于登录认证
         */
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();
        String nickname = loginRequest.getNickname();
        if (StringUtils.isAnyBlank(account, password, nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        MsSysUserDO userDO = userMapper.findUser(account, password);
        if (userDO != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), "账户已经被注册了");
        }
        userDO = new MsSysUserDO();
        userDO.setNickname(nickname);
        userDO.setAccount(account);
        userDO.setPassword(DigestUtils.md5Hex(password + slat));
        userDO.setCreateDate(System.currentTimeMillis());
        userDO.setLastLogin(System.currentTimeMillis());
        userDO.setAvatar("/static/img/logo.b3a48c0.png");
        userDO.setAdmin(1); //1 为true
        userDO.setDeleted(0); // 0 为false
        userDO.setSalt("");
        userDO.setStatus("");
        userDO.setEmail("");
        int insert = userMapper.insert(userDO);
        String token = JWTUtils.createToken(userDO.getId());
        redisTemplate.opsForValue().set("user::login::" + token, JSON.toJSONString(userDO), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("user::login::" + token);
        return Result.success(null);
    }

    @Override
    public MsSysUserDO checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("user::login::" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        MsSysUserDO sysUser = JSON.parseObject(userJson, MsSysUserDO.class);
        return sysUser;
    }

    @Override
    public Result findUserByToken(String token) {
        MsSysUserDO userDO = LoginInterceptor.threadLocal.get();
        MsSysUserDO sysUserDO=new MsSysUserDO();
        sysUserDO.setId(1573690736216416258l);
        sysUserDO.setAccount("user");
        sysUserDO.setNickname("123");
        sysUserDO.setAvatar("/static/img/logo.b3a48c0.png");
        return Result.success(userDO);
    }
}
