package truekai.cc.mapperTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import truekai.cc.mapper.MsSysUserMapper;
import truekai.cc.model.MsSysUserDO;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

                .eq(MsSysUserDO::getAccount, "daidai2")
                .last("limit 1").select(MsSysUserDO::getId))
                ;
        if(sysUserDO!=null){
            sysUserDO.setNickname("gougou");
            userMapper.updateById(sysUserDO);
        }
    }
    @Test
    void contextLoads2() {
        BigDecimal b1=new BigDecimal("0.03751");
        BigDecimal b2=new BigDecimal("100");
        BigDecimal multiply = b1.multiply(b2);
        BigDecimal bigDecimal = multiply.setScale(2, RoundingMode.FLOOR);

        System.out.println(bigDecimal);

    }
}
