package de.kieseltaucher.duplex.foreverybody.service

class BatchService {

    void simplex2Duplex(InputStream simplex, OutputStream duplex) {
        PDFBoxBatch batch = loadBatch(simplex)
        batch.simplex2Duplex()

        batch.save(duplex)
        duplex.flush()
    }

    private PDFBoxBatch loadBatch(InputStream data) {
        try {
            return PDFBoxBatch.load(data)
        } finally {
            data.close()
        }
    }
}
