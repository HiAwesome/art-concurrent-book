package chapter08;import lombok.extern.slf4j.Slf4j;import java.util.concurrent.CountDownLatch;/** *  * @author tengfei.fangtf * @version $Id: CountDownLatchTest.java, v 0.1 2015-8-1 上午12:08:52 tengfei.fangtf Exp $ */@Slf4jpublic class CountDownLatchTest {    static CountDownLatch c = new CountDownLatch(2);    public static void main(String[] args) throws InterruptedException {        new Thread(() -> {            log.info("1");            c.countDown();            log.info("2");            c.countDown();        }).start();        c.await();        log.info("3");    }}