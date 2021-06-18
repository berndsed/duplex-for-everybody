package de.kieseltaucher.duplex.foreverybody.service

import spock.lang.Specification

class BatchServiceSpec extends Specification {

    def inputBuilder = new InputBuilder()
    def service = new BatchService()

    def 'empty'() {
        expect:
        simplex2Duplex() == []
    }

    def 'one page'() {
        given:
        inputBuilder.addPage(1)
        expect:
        simplex2Duplex() == [1]
    }

    def 'three pages'() {
        given:
        inputBuilder.addPage(1)
        inputBuilder.addPage(3)
        inputBuilder.addPage(2)
        expect:
        simplex2Duplex() == [1, 2, 3]
    }

    def simplex2Duplex() {
        def duplexPdf = new TestPDF()
        service.simplex2Duplex(inputBuilder.build(), duplexPdf.loader())
        return duplexPdf.pages()
    }
}

class InputBuilder {

    TestPDF data = new TestPDF()

    void addPage(int number) {
        data.addPage number
    }

    InputStream build() {
        data.binary()
    }
}
