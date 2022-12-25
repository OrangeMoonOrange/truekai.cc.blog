package truekai.cc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author truekai
 * @since 2022-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ms_comment")
public class MsCommentDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private Long createDate;

    private Integer articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private String level;


}
