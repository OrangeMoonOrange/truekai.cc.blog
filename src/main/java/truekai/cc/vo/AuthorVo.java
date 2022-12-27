package truekai.cc.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 作者：熊凯凯
 * 日期：2022-12-24 21:36
 */
@Data
public class AuthorVo implements Serializable {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


}
