package truekai.cc.main;

/**
 * 作者：熊凯凯
 * 日期：2023-01-19 09:16
 */
public class ExectorThread implements Runnable{
    @Override
    public void run() {
        LazySimpleSingleton instance = LazySimpleSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+":"+instance);
    }
}
