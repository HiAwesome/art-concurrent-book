package chapter05;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 10-19
 */
public class ProcessData {
    private static final ReentrantReadWriteLock RWL = new ReentrantReadWriteLock();
    private static final Lock READ_LOCK = RWL.readLock();
    private static final Lock WRITE_LOCK = RWL.writeLock();
    private volatile boolean update = false;

    public void processData() {
        READ_LOCK.lock();
        if (!update) {
            // 必须先释放读锁
            READ_LOCK.unlock();
            // 锁降级从写锁获取到开始
            WRITE_LOCK.lock();
            try {
                if (!update) {
                    // 准备数据的流程（略）
                    update = true;
                }
                READ_LOCK.lock();
            } finally {
                WRITE_LOCK.unlock();
            }
            // 锁降级完成，写锁降级为读锁
        }
        //noinspection EmptyTryBlock
        try {
            // 使用数据的流程（略）
        } finally {
            READ_LOCK.unlock();
        }
    }

}
