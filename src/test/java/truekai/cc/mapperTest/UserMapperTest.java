package truekai.cc.mapperTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import truekai.cc.mapper.MsSysUserMapper;
import truekai.cc.model.MsSysUserDO;

/**
 * 作者：熊凯凯
 * 日期：2022-12-23 17:33
 */
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private DruidDataSource dataSource;

    @Autowired
    private MsSysUserMapper userMapper;


    @Test
    void contextLoads() {
        MsSysUserDO sysUserDO = userMapper.selectOne(new LambdaQueryWrapper<MsSysUserDO>()
                        .select(MsSysUserDO::getId)
                .eq(MsSysUserDO::getAccount, "daidai2"));
        if(sysUserDO!=null){
            sysUserDO.setNickname("gougou");
            userMapper.updateById(sysUserDO);
        }
    }
}
