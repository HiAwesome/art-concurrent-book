package chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 6-4
 */
public class SleepUtils {
    public static void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {
        }
    }
}
