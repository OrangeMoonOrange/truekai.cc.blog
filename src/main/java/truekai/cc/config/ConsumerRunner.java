package truekai.cc.config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import truekai.cc.mapper.MsConfigMapper;
import truekai.cc.mapper.MsSysUserMapper;
import truekai.cc.model.MsConfigDO;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 21:54
 */
@Component
public class ConsumerRunner implements ApplicationRunner {

    @Autowired
    private MsSysUserMapper userMapper;

    @Autowired
    private MsConfigMapper configMapper;

    @Value("${aliyun.oss.configId}")
    private int OOSConfigId;


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("启动springboot后去数据库加载数据");
        OSSConfig bean = applicationContext.getBean("oSSConfig", OSSConfig.class);
        MsConfigDO msConfigDO = configMapper.selectById(OOSConfigId);
        OSSConfig ossConfig = JSON.parseObject(msConfigDO.getJson(), OSSConfig.class);
        BeanUtils.copyProperties(ossConfig, bean);

    }


}
