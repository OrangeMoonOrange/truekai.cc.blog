package truekai.cc.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 18:25
 */

@Component
@Data
public class CustomerId {
    private final static long START_TIME = 1554048000000l;
    private static long MACHINE_ID = 21L;
    private final static long MAX_DATA_CENTER_ID = 127L;
    private final static long MAX_SEQUENCE = 255L;
    private final static long DATA_CENTER_BIT = 8L;
    // 机器中心位数￼
    private final static long MACHINE_BIT = 6L;
    // 序列编号占位位数￼
    private final static long SEQUENCE_BIT = 8L;
    // 数据中心移位（8位）￼
    private final static long DATA_CENTER_SHIFT = SEQUENCE_BIT;
    // 当前发号节点移位（8+8=16位）￼
    private final static long MACHINE_SHIFT = SEQUENCE_BIT + DATA_CENTER_BIT;
    // 时间戳移位（8+8+6=22位）￼
    private final static long TIMESTAMP_SHIFT = SEQUENCE_BIT + DATA_CENTER_BIT + MACHINE_BIT;

    //数据中心编号
    private long dataCenterId = 1l;
    //序号
    private long sequence = 0;
    //上次时间戳
    private long lastTimestamp;

    public CustomerId(@Value("${dataCenterId}") long dataCenterId) {
        if (dataCenterId > MAX_DATA_CENTER_ID) {
            throw new RuntimeException();
        }
        if (dataCenterId < 0) {
            throw new RuntimeException();
        }
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        //获取当前时间
        long timestamp = System.currentTimeMillis();
        //如果是同一个毫秒时间戳的处理
        if (timestamp == lastTimestamp) {
            sequence += 1;
            if (sequence > MAX_SEQUENCE) {
                sequence = 0;
                timestamp = tilNextMillis(timestamp);
            }
        } else {
            //修改时间戳
            lastTimestamp = timestamp;
            //序号重新开始
            sequence = 0;
        }
        long result = ((timestamp - START_TIME) << TIMESTAMP_SHIFT)
                | (MACHINE_ID << MACHINE_SHIFT)
                | (this.dataCenterId << DATA_CENTER_SHIFT)
                | sequence;
        return result;
    }


    /**
     * 阻塞到下一秒。直到获得新的时间戳
     *
     * @param timestamp 上一次生成ID的时间戳
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        do {
            timestamp = System.currentTimeMillis();
        } while (timestamp > lastTimestamp);
        return timestamp;
    }

    public static void main(String[] args) {
        CustomerId customerId = new CustomerId(1);
        System.out.println(customerId.nextId() >Long.MAX_VALUE);
    }
}
