package truekai.cc.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import truekai.cc.mapper.MsSysUserMapper;
import truekai.cc.model.MsSysUserDO;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 21:54
 */
@Component
public class ConsumerRunner implements ApplicationRunner {

    @Autowired
    private MsSysUserMapper userMapper;


    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("启动springboot后去数据库加载数据");
        MsSysUserDO sysUserDO = userMapper.selectOne(new LambdaQueryWrapper<MsSysUserDO>()
                .eq(MsSysUserDO::getId, "1"));
        OSSConfig2 bean = applicationContext.getBean("oSSConfig2", OSSConfig2.class);
        System.out.println(bean);

        bean.setBucketname(sysUserDO.getAccount());
        OSSConfig2 bean2 = applicationContext.getBean("oSSConfig2", OSSConfig2.class);
        System.out.println(bean2);

    }


}
