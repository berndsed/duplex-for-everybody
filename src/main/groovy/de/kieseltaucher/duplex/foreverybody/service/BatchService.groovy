package de.kieseltaucher.duplex.foreverybody.service

class BatchService {

    private static final def IS_MAL_PDF_ERROR = (~"Error: End-of-File.*").asMatchPredicate()

    void simplex2Duplex(InputStream simplex, OutputStream duplex) {
        PDFBoxBatch batch = loadBatch(simplex)
        batch.simplex2Duplex()

        batch.save(duplex)
        duplex.flush()
    }

    private PDFBoxBatch loadBatch(InputStream data) {
        try {
            return PDFBoxBatch.load(data)
        } catch (IOException e) {
            throw mapLoadException(e)
        } finally {
            data.close()
        }
    }

    private Exception mapLoadException(IOException e) {
        return IS_MAL_PDF_ERROR.test(e.getMessage()) ? new MalformedPDFException(e) : e
    }
}
