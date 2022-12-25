package truekai.cc.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import truekai.cc.request.LoginRequest;
import truekai.cc.service.MsSysUserService;
import truekai.cc.vo.Result;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author truekai
 * @since 2022-12-23
 */
@RestController
@Slf4j
public class MsSysUserController {
    @Autowired
    private MsSysUserService msSysUserService;

    @PostMapping("login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        log.info("用户登录接口参数：，{}", loginRequest);
        Result result = msSysUserService.login(loginRequest);
        return result;
    }

    @PostMapping("register")
    public Result register(@RequestBody LoginRequest loginRequest) {
        log.info("用户注册接口参数：，{}", loginRequest);
        Result result = msSysUserService.register(loginRequest);
        return result;
    }

    @GetMapping("logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        log.info("用户登出接口参数：，{}", token);
        Result result = msSysUserService.logout(token);
        return result;
    }

    @GetMapping("users/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return msSysUserService.findUserByToken(token);
    }

}

