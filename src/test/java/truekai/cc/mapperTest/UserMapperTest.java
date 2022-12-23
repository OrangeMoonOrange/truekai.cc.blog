package truekai.cc.mapperTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import truekai.cc.mapper.MsSysUserMapper;
import truekai.cc.model.MsSysUserDO;

import java.util.List;

/**
 * 作者：熊凯凯
 * 日期：2022-12-23 17:33
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private MsSysUserMapper userMapper;


    @Test
    void contextLoads() {
        List<MsSysUserDO> msSysUserDOS = userMapper.selectList(null);
        System.out.println(msSysUserDOS);
    }
}
