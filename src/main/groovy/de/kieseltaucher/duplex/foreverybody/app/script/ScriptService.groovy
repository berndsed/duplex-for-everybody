package de.kieseltaucher.duplex.foreverybody.app.script

import de.kieseltaucher.duplex.foreverybody.service.BatchService
import de.kieseltaucher.duplex.foreverybody.service.LazyOutputStream

import java.util.function.Consumer
import java.util.function.Supplier

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
        final Supplier<OutputStream> targetSource = { new BufferedOutputStream(new FileOutputStream(file)) }
        final OutputStream output = new LazyOutputStream(targetSource)
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

}
