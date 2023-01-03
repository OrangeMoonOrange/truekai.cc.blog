package truekai.cc.interceptor;

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

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");
        if (token == null) {
            token = request.getParameter("Authorization");
        }
        log.info("=================request start===========================");
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        //ToDo 简单处理跨域 OPTIONS请求，后续需要改进
        if (method.equalsIgnoreCase("OPTIONS")
                && ("/api/users/currentUser".equalsIgnoreCase(requestURI)
                || "/api/tags".equalsIgnoreCase(requestURI)
                || "/api/categorys".equalsIgnoreCase(requestURI)
                || "/api/articles/publish".equalsIgnoreCase(requestURI)
                || "/api/comments/create/change".equalsIgnoreCase(requestURI))) {
            return true;
        }

        //特殊判断评论接口

        if (StringUtils.isNotBlank(token)) {
            MsSysUserDO userDO = userService.checkToken(token);
            if (userDO == null) {
                CommonUtil.sendJsonMessage(response, Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录"));
                return false;
            }
            threadLocal.set(userDO);
            MsSysUserDO sysUserDO = threadLocal.get();
            return true;
        } else {
            if(requestURI.contains("/comments/create/change")){
                return true;
            }
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
