package truekai.cc.mapperTest;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import truekai.cc.mapper.MsSysUserMapper;

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
        System.out.println(dataSource.getProperties());
        System.out.println(dataSource.getMaxActive());
        System.out.println(dataSource.getClass());
    }
}
