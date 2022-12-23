package truekai.cc.request;

import lombok.Data;

/**
 * 作者：熊凯凯
 * 日期：2022-12-23 18:21
 * 登录请求
 */
@Data
public class LoginRequest {
    /**
     * 用户名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

}
