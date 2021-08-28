package chapter04;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 6-12
 */
@Slf4j
public class Piped {

    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);

        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } finally {
            out.close();
        }
    }

    static class Print implements Runnable {
        private final PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        public void run() {
            int receive;
            try {
                while ((receive = in.read()) != -1) {
                    log.info("(char) receive:{}", (char) receive);
                }
            } catch (IOException ignored) {
            }
        }
    }
}
