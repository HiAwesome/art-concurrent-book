package chapter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 6-5
 */
@Slf4j
public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner());
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(100);
            } finally {
                log.info("DaemonThread finally run.");
            }
        }
    }
}
