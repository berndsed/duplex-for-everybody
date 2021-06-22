package de.kieseltaucher.duplex.foreverybody.service

import spock.lang.Specification

class BatchServiceSpec extends Specification {

    def simplexPdf = new TestPDF()
    def service = new BatchService()

    def 'empty'() {
        expect:
        simplex2Duplex() == []
    }

    def 'one page'() {
        given:
        simplexPdf.addPage(1)
        expect:
        simplex2Duplex() == [1]
    }

    def 'three pages'() {
        given:
        simplexPdf.addPage(1)
        simplexPdf.addPage(3)
        simplexPdf.addPage(2)
        expect:
        simplex2Duplex() == [1, 2, 3]
    }

    def simplex2Duplex() {
        def duplexPdf = new TestPDF()
        service.simplex2Duplex(simplexPdf.binary(), duplexPdf.loader())
        return duplexPdf.pages()
    }
}

