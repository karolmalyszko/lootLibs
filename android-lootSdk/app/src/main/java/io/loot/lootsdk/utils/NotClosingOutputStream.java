package io.loot.lootsdk.utils;

import java.io.IOException;
import java.io.OutputStream;

class NotClosingOutputStream extends OutputStream {
    private final OutputStream os;

    NotClosingOutputStream(OutputStream os) {
        this.os = os;
    }

    @Override
    public void write(int b) throws IOException {
        os.write(b);
    }

    @Override
    public void close() throws IOException {
        // not closing the stream.
    }

    @Override
    public void flush() throws IOException {
        os.flush();
    }

    @Override
    public void write(byte[] buffer, int offset, int count) throws IOException {
        os.write(buffer, offset, count);
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        os.write(buffer);
    }
}