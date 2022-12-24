package truekai.cc.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import truekai.cc.enumCode.ErrorCode;
import truekai.cc.model.MsSysUserDO;
import truekai.cc.service.MsSysUserService;
import truekai.cc.utils.CommonUtil;
import truekai.cc.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：熊凯凯
 * 日期：2022-12-23 21:56
 */
@Slf4j
@Configuration
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MsSysUserDO> threadLocal = new ThreadLocal<>();

    @Autowired
    private MsSysUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //这个方法会在执行controller方法之前进行执行
        /**
         * 1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
         * 2. 判断 token是否为空，如果为空 未登录
         * 3. 如果token 不为空，登录验证 loginService checkToken
         * 4. 如果认证成功 放行即可
         */


        String token = request.getHeader("Authorization");
        if (token == null) {
            token = request.getParameter("Authorization");
        }
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        if (StringUtils.isNotBlank(token)) {
            MsSysUserDO userDO = userService.checkToken(token);
            if (userDO == null) {
                CommonUtil.sendJsonMessage(response, Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录"));
                return false;
            }
            threadLocal.set(userDO);
            return true;
        }
        CommonUtil.sendJsonMessage(response, Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录"));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}