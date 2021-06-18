package de.kieseltaucher.duplex.foreverybody.service

import spock.lang.Specification

class TestPDFSpec extends Specification {

    def 'counts pages'() {
        given:
        def pdf = new TestPDF()

        expect:
        pdf.pageCount() == 0

        when:
        pdf.addPage 1
        then:
        pdf.pageCount() == 1
    }

    def 'remembers page numbers'() {
        given:
        def pdf = new TestPDF()

        expect:
        pdf.pages() == []

        when:
        pdf.addPage 1
        pdf.addPage 2

        then:
        pdf.pages() == [1, 2]
    }

    def 'saves and loads'() {
        given:
        def created = new TestPDF()
        created.addPage 1
        def loaded = new TestPDF()

        when:
        def loader = loaded.loader()
        loader.write created.binary().bytes
        loader.close()

        then:
        loaded.pageCount() == 1
        loaded.pages() == [1]
    }

}
