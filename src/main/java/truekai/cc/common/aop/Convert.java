package truekai.cc.common.aop;

import truekai.cc.model.OperateLogDo;

public interface Convert<parm> {
    /**
     * 将入参转为标砖模型
     * @param p
     * @return
     */
    OperateLogDo convert(parm p);
}
