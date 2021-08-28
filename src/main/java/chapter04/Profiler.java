package chapter04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 6-15
 */
@Slf4j
public class Profiler {
    // 第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREAD_LOCAL =
            ThreadLocal.withInitial(System::currentTimeMillis);

    public static void begin() {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    public static long end() {
        return System.currentTimeMillis() - TIME_THREAD_LOCAL.get();
    }

    public static void main(String[] args) throws Exception {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        log.info("Cost: " + Profiler.end() + " mills");
    }
}
