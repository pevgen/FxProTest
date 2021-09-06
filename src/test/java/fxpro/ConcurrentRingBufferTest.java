package fxpro;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConcurrentRingBufferTest {

    private final static int TEST_ELEMENT_COUNT = 10;

    @Test
    public void testPutAndGetFromDifferentThreads() throws InterruptedException {

        var buffer = new ConcurrentRingBuffer(1);
        var isError = new AtomicBoolean(false);

        var writingThread = new Thread(new TestRunnable(isError, i -> {
            try {
                buffer.put(i);
                System.out.println("Put : " + i);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));


        var readingThread = new Thread(new TestRunnable(isError, i -> {
            try {
                System.out.println("Get : " + buffer.get());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));


        writingThread.start();
        readingThread.start();

        writingThread.join();
        readingThread.join();

        assertFalse(isError.get());
        assertEquals(0, buffer.getFilledValuesSize());
    }


    private static class TestRunnable implements Runnable {

        Consumer<Integer> bufferConsumer;
        AtomicBoolean isError;

        public TestRunnable(AtomicBoolean isError, Consumer<Integer> bufferConsumer) {
            this.isError = isError;
            this.bufferConsumer = bufferConsumer;

        }

        @Override
        public void run() {
            Random rr = new Random();
            for (int i = 0; i < TEST_ELEMENT_COUNT; i++) {
                try {
                    Thread.sleep(rr.nextInt(5) * 100);
                } catch (InterruptedException e) {
                }
                try {
                    bufferConsumer.accept(i);
                } catch (IllegalStateException exc) {
                    exc.printStackTrace();
                    isError.set(true);
                    break;
                }
            }
        }
    }

}