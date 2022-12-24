package truekai.cc.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("ms_article_body")
public class MsArticleBodyDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;


}
