package truekai.cc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@TableName("ms_tag")
public class MsTagDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.INPUT)
      @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String avatar;

    private String tagName;


}
