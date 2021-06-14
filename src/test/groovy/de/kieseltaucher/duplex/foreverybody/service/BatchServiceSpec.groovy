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
        def out = new ByteArrayOutputStream()
        service.simplex2Duplex(inputBuilder.build(), out)
        return new ByteArrayInputStream(out.toByteArray())
                .withReader { reader -> reader.text.split().collect { Integer.parseInt(it) } }
    }
}

class InputBuilder {

    String data = ""

    void addPage(int number) {
        if (!data.isEmpty()) {
            data += " "
        }
        data += Integer.toString(number)
    }

    InputStream build() {
        new ByteArrayInputStream(data.getBytes())
    }
}
