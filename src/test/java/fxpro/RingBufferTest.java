package fxpro;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RingBufferTest {

    @Test(expected = IllegalStateException.class)
    public void testReadFromEmptyBuffer() {
        RingBuffer buffer = new RingBuffer(0);
        buffer.get();
    }

    @Test(expected = IllegalStateException.class)
    public void testPutIntoFullBuffer() {
        RingBuffer buffer = new RingBuffer(0);
        buffer.put(new Object());
    }

    @Test
    public void testPutAndGetOneElement() {
        RingBuffer buffer = new RingBuffer(1);
        var element = 1;
        buffer.put(element);
        assertEquals(element, buffer.get());
    }

    @Test
    public void testPutAndGetFifo() {
        RingBuffer buffer = new RingBuffer(2);
        var element1 = 1;
        var element2 = 2;
        buffer.put(element1);
        buffer.put(element2);
        assertEquals(element1, buffer.get());
        assertEquals(element2, buffer.get());
    }

    @Test
    public void testPutAndGetFifoInsideBufferSize() {
        RingBuffer buffer = new RingBuffer(50);
        var element1 = 1;
        buffer.put(element1);
        assertEquals(element1, buffer.get());
        var element2 = 2;
        buffer.put(element2);
        assertEquals(element2, buffer.get());
    }

    @Test
    public void testPutAndGetFifoInCycle() {
        RingBuffer buffer = new RingBuffer(2);
        var element1 = 1;
        buffer.put(element1);
        assertEquals(element1, buffer.get());
        var element2 = 2;
        buffer.put(element2);
        var element3 = 3;
        buffer.put(element3);
        assertEquals(element2, buffer.get());
        assertEquals(element3, buffer.get());
    }


}