package de.kieseltaucher.duplex.foreverybody.service

class BatchService {

    void simplex2Duplex(InputStream simplex, OutputStream duplex) {
        PDFBoxBatch batch
        try {
            batch = PDFBoxBatch.load(simplex)
        } finally {
            simplex.close()
        }
        batch.simplex2Duplex()

        batch.save(duplex)
        duplex.flush()
    }

}
