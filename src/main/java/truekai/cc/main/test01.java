package truekai.cc.main;

/**
 * 作者：熊凯凯
 * 日期：2023-01-17 23:20
 */
public class test01 {
    public static void main(String[] args) {
       Thread thread1=new Thread(new ExectorThread());
        Thread thread2=new Thread(new ExectorThread());
        thread1.start();
        thread2.start();
        System.out.println("end");
        /**
         * Thread-1:truekai.cc.main.LazySimpleSingleton@13f4e178
         * Thread-0:truekai.cc.main.LazySimpleSingleton@7b03af26
         */
    }

    public int test(){
        int a=1;
        try {
            a=a+122;
            return a;
        }finally {
            a=2;
            return a;

        }
    }
}
