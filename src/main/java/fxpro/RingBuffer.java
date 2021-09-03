package fxpro;

/*
1. FIFO
2. Ring buffer, use all space in the buffer. Throw exception in corner cases: no free space (on put) or data (on get)

e.g.

empty buffer _,_,_
get -> no data, buffer is empty (exception: non thread-save , block: thread-safe)
put 6 -> 6,_,_
put 7 -> 6,7,_
put 8 -> 6,7,8
put 9 -> buffer is full (exception: non thread-save , block: thread-safe)
get -> 6 _,7,8
put 9 -> 9,7,8
get -> 7 9,_,8

3. blocking when no free place (on put) or data (on get)
4. Thread safe
*/

public class RingBuffer {

    private final int MAX_BUF_SIZE;
    private int filledValuesSize = 0;
    private int readPointer = 0;
    private int writePointer = 0;

    private final Object[] data;

    public RingBuffer(int maxBufferSize) {
        MAX_BUF_SIZE = maxBufferSize;

        data = new Object[MAX_BUF_SIZE];
    }


    public void put(Object o) {
        if (filledValuesSize == data.length) {
            throw new IllegalStateException();
        }
        data[writePointer] = o;
        writePointer++;
        if (writePointer == data.length) {
            writePointer = 0;
        }
        filledValuesSize++;
    }

    public Object get() {
        if (filledValuesSize == 0) {
            throw new IllegalStateException();
        }

        filledValuesSize--;
        var value = data[readPointer];
        readPointer++;
        if (readPointer == data.length) {
            readPointer = 0;
        }

        return value;
    }

}