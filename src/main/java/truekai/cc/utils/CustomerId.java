package truekai.cc.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 18:25
 */
@Component
public class CustomerId {
    public synchronized long nextId() {
        return new Random().nextInt(10000000-10)+20;
    }
}
