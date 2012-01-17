package junk;

import java.io.IOException;

public class ChunkHelper {
    public static int SIZE_BYTES = 2;
    public final byte[] buf;

    public ChunkHelper(byte[] buf) {
        if (!checkLength(buf.length - SIZE_BYTES, 0xFFFF)) {
            throw new IllegalArgumentException();
        }
        this.buf = buf;
    }

    public int getLength() throws IOException {
        int length = ((buf[0] & 0xFF) << 8) + (buf[1] & 0xFF);
        if (!checkLength(length)) {
            throw new IOException("invalid length: " + length);
        }
        return length;
    }

    public void setLength(int length) throws IOException {
        if (!checkLength(length)) {
            throw new IOException("invalid length: " + length);
        }
        buf[0] = (byte)(length >>> 8);
        buf[1] = (byte) length;
    }

    private boolean checkLength(int length) {
        return checkLength(length, buf.length - SIZE_BYTES);
    }

    private boolean checkLength(int length, int maxLength) {
        return (length > 0) && (length <= maxLength);
    }

}
