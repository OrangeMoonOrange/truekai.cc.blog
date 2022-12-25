package truekai.cc.interceptor;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：熊凯凯
 * 日期：2022-12-24 19:51
 */
@Data
public class ArticleListRequest implements Serializable {
    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    /**
     * 文章归档查询
     */
    private String year;

    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
