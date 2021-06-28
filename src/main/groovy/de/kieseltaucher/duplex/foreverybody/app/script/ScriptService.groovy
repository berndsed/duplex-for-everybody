package de.kieseltaucher.duplex.foreverybody.app.script

import de.kieseltaucher.duplex.foreverybody.service.BatchService

import java.util.function.Consumer

class ScriptService implements Consumer<File> {

    private final BatchService batchService = new BatchService()

    @Override
    void accept(File file) {
        if (file != null) {
            convertFile(file)
        } else {
            pipe()
        }
    }

    private void convertFile(File file) {
        final InputStream input = new BufferedInputStream(new FileInputStream(file))
        final OutputStream output = new BufferedOutputStream(new LazyFileOutputStream(file))
        try {
            batchService.simplex2Duplex(input, output)
        } finally {
            try {
                output.close()
            } finally {
                input.close()
            }
        }
    }

    private void pipe() {
        batchService.simplex2Duplex(System.in, System.out)
    }

    private class LazyFileOutputStream extends OutputStream {

        private final File file
        private OutputStream out

        LazyFileOutputStream(File file) {
            this.file = file
        }

        @Override
        void write(byte[] b) throws IOException {
            out().write(b)
        }

        @Override
        void write(byte[] b, int off, int len) throws IOException {
            out().write(b, off, len)
        }

        @Override
        void flush() throws IOException {
            out().flush()
        }

        @Override
        void close() throws IOException {
            out().close()
        }

        @Override
        void write(int b) throws IOException {
            out().write(b)
        }

        OutputStream out() {
            if (out == null) {
                out = new FileOutputStream(file)
            }
            return out
        }
    }

}
