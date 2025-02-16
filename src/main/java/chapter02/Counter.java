/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package chapter02;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器
 *
 * @author tengfei.fangtf
 * @version $Id: Snippet.java, v 0.1 2015-7-31 下午11:32:42 tengfei.fangtf Exp $
 */
@Slf4j
public class Counter {

    private final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    private int NORMAL_INT = 0;

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    cas.count();
                    cas.safeCount();
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();

        }
        // 等待所有线程执行完成
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        log.info("normal int: {}", cas.NORMAL_INT);
        log.info("atomic integer: {}", cas.ATOMIC_INTEGER.get());
        log.info("time: {}", System.currentTimeMillis() - start);
    }

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = ATOMIC_INTEGER.get();
            boolean suc = ATOMIC_INTEGER.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        NORMAL_INT++;
    }

}
