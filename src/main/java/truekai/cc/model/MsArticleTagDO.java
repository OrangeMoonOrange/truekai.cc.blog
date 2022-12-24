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
 * @since 2022-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ms_article_tag")
public class MsArticleTagDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long articleId;

    private Long tagId;


}
