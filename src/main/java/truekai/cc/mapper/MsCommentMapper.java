package truekai.cc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import truekai.cc.model.MsCommentDO;
import truekai.cc.vo.CommentVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author truekai
 * @since 2022-12-25
 */
public interface MsCommentMapper extends BaseMapper<MsCommentDO> {


    /**
     *
     * @param id 文章id
     * @return
     */
    List<CommentVo> selectListById(@Param("id") Long id,@Param("mcId") String mcId);
}
