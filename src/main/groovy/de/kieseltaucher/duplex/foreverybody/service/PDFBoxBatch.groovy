package de.kieseltaucher.duplex.foreverybody.service

import de.kieseltaucher.duplex.foreverybody.core.Batch
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage

class PDFBoxBatch implements Batch<PDPage> {

    static PDFBoxBatch load(InputStream data) {
        final PDDocument document = PDDocument.load(data)
        new PDFBoxBatch(document)
    }

    private final PDDocument originalDocument
    private final List<PDPage> pages

    private PDFBoxBatch(PDDocument document) {
        this.originalDocument = document
        this.pages = new LinkedList<>()
        def iterator = document.pages.iterator()
        while(iterator.hasNext()) {
            pages.add iterator.next()
        }
    }

    @Override
    void add(int idx, PDPage element) {
        pages.add(idx, element)
    }

    @Override
    PDPage remove(int idx) {
        pages.removeAt(idx)
    }

    @Override
    int size() {
        pages.size()
    }

    void save(OutputStream target) {
        PDDocument document = new PDDocument()
        pages.forEach {document.addPage it}
        document.save target
        document.close()
    }
}
