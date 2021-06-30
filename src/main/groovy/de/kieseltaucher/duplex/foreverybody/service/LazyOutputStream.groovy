package de.kieseltaucher.duplex.foreverybody.service

import java.util.function.Supplier

class LazyOutputStream extends OutputStream {

    private final Supplier<OutputStream> source
    private OutputStream out
    private boolean closed

    LazyOutputStream(Supplier<OutputStream> source) {
        this.source = source
    }

    @Override
    void write(byte[] b) throws IOException {
        out().write b
    }

    @Override
    void write(byte[] b, int off, int len) throws IOException {
        out().write b, off, len
    }

    @Override
    void write(int b) throws IOException {
        out().write b
    }

    @Override
    void flush() throws IOException {
        if (out != null) {
            out.flush()
        }
    }

    @Override
    void close() throws IOException {
        closed = true
        if (out != null) {
            out.close()
        }
    }

    private OutputStream out() {
        if (closed) {
            throw new IOException('Stream already closed')
        }
        if (out == null) {
            out = source.get()
        }
        out
    }
}
