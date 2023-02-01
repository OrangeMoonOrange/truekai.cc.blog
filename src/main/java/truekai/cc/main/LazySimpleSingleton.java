package truekai.cc.main;

/**
 * 作者：熊凯凯
 * 日期：2023-01-19 09:11
 * 简单的单例模式
 */
public class LazySimpleSingleton {
    private LazySimpleSingleton() {
    };
    private static LazySimpleSingleton lazySimpleSingleton;

    public static LazySimpleSingleton getInstance() {
        if (lazySimpleSingleton == null) {
            lazySimpleSingleton = new LazySimpleSingleton();
        }
        return lazySimpleSingleton;
    }
}
