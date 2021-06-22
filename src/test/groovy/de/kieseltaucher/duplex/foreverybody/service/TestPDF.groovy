package de.kieseltaucher.duplex.foreverybody.service

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.text.PDFTextStripper

class TestPDF {

    static InputStream empty() {
        new TestPDF().binary()
    }

    private PDDocument document = new PDDocument()

    OutputStream loader() {
        def loader = new ByteArrayOutputStream() {
            @Override
            void close() throws IOException {
                super.close()
                def written = toByteArray()
                document = PDDocument.load written
            }
        }
        return loader
    }

    void addPage(int number) {
        def page = new PDPage()
        document.addPage(page)
        def content = new PDPageContentStream(document, page)
        content.beginText()
        content.setFont PDType1Font.HELVETICA, 12
        content.showText Integer.toString(number)
        content.endText()
        content.close()
    }

    int pageCount() {
        document.getPages().size()
    }

    List<Integer> pages() {
        def text = new PDFTextStripper().getText document
        text.split().collect { Integer.parseInt(it) }
    }

    InputStream binary() {
        def out = new ByteArrayOutputStream()
        document.save(out)
        new ByteArrayInputStream(out.toByteArray())
    }

    InputStream brokenBinary() {
        def out = new ByteArrayOutputStream()
        document.save(out)
        byte[] bytes = out.toByteArray()
        for (int iter = 0; iter < bytes.length; ++iter) {
            bytes[iter] = ~bytes[iter]
        }
        new ByteArrayInputStream(bytes)
    }

}
