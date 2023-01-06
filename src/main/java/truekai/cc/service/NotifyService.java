package truekai.cc.service;


import truekai.cc.vo.Result;

public interface NotifyService {

    /**
     * 发送验证码
     *
     * @param to
     * @return
     */
    Result senEmail(String to);


}
