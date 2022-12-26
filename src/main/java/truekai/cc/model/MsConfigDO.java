package truekai.cc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * oos配置
 * </p>
 *
 * @author truekai
 * @since 2022-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ms_config")
public class MsConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String json;

    @TableField("className")
    private String classname;


}
