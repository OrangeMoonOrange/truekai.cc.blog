package truekai.cc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 01:55
 */
public class pageList {
    //自定义分页方法                                     页数           每页条数
    public static PageVo<Object> pageList(List<Object> list, Integer pageNum, Integer pageSize) {
        PageVo<Object> pageVo = new PageVo<>();
        //设置总条数
        pageVo.setTotal(list.size());
        //判断集合为空
        if (list.size() == 0) {
            pageVo.setList(list);
            return pageVo;
        }
        //判断页数为1
        if (pageSize.compareTo(1) == 0) {
            ArrayList<Object> listVo = new ArrayList<>();
            Object t = list.get(pageNum - 1);
            listVo.add(t);
            pageVo.setList(listVo);
            return pageVo;
        } else {
            HashMap<Integer, List<Object>> pageMap = new HashMap<>();
            //循环集合
            Integer num = 0;
            for (int i = 1; i <= list.size(); i++) {
                if (list.size() == 1) {
                    pageMap.put(num, list);
                    break;
                }
                if (i % pageSize == 0) {
                    pageMap.get(num).add(list.get(i - 1));
                    pageMap.put(num, pageMap.get(num));
                    num++;
                } else {
                    if (pageMap.get(num) == null) {
                        List<Object> t = new ArrayList<>();
                        t.add(list.get(i - 1));
                        pageMap.put(num, t);
                    } else {
                        pageMap.get(num).add(list.get(i - 1));
                        pageMap.put(num, pageMap.get(num));
                    }
                }
            }
            List<Object> t = pageMap.get(pageNum - 1);
            pageVo.setList(t);
            return pageVo;
        }
    }
}
