package truekai.cc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

      @TableId(value = "id", type = IdType.INPUT)
      @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String content;

    private Date createDate;

    private Long articleId;

    private Long authorId;

    private String parentId;

    private Long toUid;

    private String level;

    @TableField(exist = false)
    private List<MsCommentDO> childrens;


}
