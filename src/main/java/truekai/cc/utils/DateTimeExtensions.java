package truekai.cc.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 15:33
 */

/// <summary>
/// 日期扩展
/// </summary>
public class DateTimeExtensions  {
    public static void main(String[] args) throws Exception {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(df.format(1672973617066l));
        //1361325960
        long epoch = df.parse("2013-02-20 10:06:00").getTime();
        System.out.println("should be 1361325960 ："+epoch);
        long l = System.currentTimeMillis();
        System.out.println(l);


        Date d=new Date();
        String t=df.format(d);
        epoch=df.parse(t).getTime()/1000;
        System.out.println("t is ："+t+",unix stamp is "+epoch);

    }



}


