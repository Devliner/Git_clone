package com.probufDemo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create by WangLei 2018/6/15 12:46
 * description:
 */
public class DataOutputOutputStream extends OutputStream {
    private final DataOutput out;

    /**
     * Construct an OutputStream from the given DataOutput. If 'out'
     * is already an OutputStream, simply returns it. Otherwise, wraps
     * it in an OutputStream.
     * @param out the DataOutput to wrap
     * @return an OutputStream instance that outputs to 'out'
     */
    public static OutputStream constructOutputStream(DataOutput out) {
        if (out instanceof OutputStream) {
            return (OutputStream)out;
        } else {
            return new DataOutputOutputStream(out);
        }
    }

    private DataOutputOutputStream(DataOutput out) {
        this.out = out;
    }


    public void write(int b) throws IOException {
        out.writeByte(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    public void write(byte[] b) throws IOException {
        out.write(b);
    }
}
