package truekai.cc.utils;

import lombok.Data;

import java.util.List;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 01:56
 */
@Data
public class PageVo<Object>{

    //name ="list",value ="数据list")
    private List<Object> list;

    //(name ="total",value ="总条数")
    private Integer total;
}
