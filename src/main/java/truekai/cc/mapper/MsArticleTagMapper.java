package truekai.cc.mapper;

import org.apache.ibatis.annotations.Param;
import truekai.cc.model.MsArticleTagDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author truekai
 * @since 2022-12-24
 */
public interface MsArticleTagMapper extends BaseMapper<MsArticleTagDO> {

    /**
     * 最热标签查询
     * @param hotTaglimit
     * @return
     */
    List<MsArticleTagDO> hots(@Param("limit") Integer hotTaglimit);
}
