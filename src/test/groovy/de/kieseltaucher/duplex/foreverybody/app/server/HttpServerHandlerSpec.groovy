package de.kieseltaucher.duplex.foreverybody.app.server

import de.kieseltaucher.duplex.foreverybody.service.TestPDF
import spock.lang.Specification

class HttpServerHandlerSpec extends Specification {

    def handler = new HttpServerHandler()
    def httpExchange = new TestHttpExchange()

    void 'converts pdf'() {
        given:
        def pdf = new TestPDF()
        pdf.addPage(1)
        httpExchange.requestBody = pdf.binary()

        def result = new TestPDF()
        httpExchange.responseBody = result.loader()

        when:
        handler.handle(httpExchange)

        then:
        httpExchange.responseCode == 200
        result.pages() == [1]
    }

    void 'Method-Not-Allowed when not post'(String method) {
        given:
        httpExchange.requestMethod = method

        when:
        handler.handle(httpExchange)

        then:
        httpExchange.responseCode == 405

        where:
        method | _
        'GET'  | _
        'PUT'  | _
    }

}
