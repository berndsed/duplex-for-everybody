package de.kieseltaucher.duplex.foreverybody.app.server

import de.kieseltaucher.duplex.foreverybody.service.TestPDF
import spock.lang.Specification

class HttpServerHandlerSpec extends Specification {

    def handler = new HttpServerHandler()
    TestHttpExchange httpExchange = new TestHttpExchange()

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
        httpExchange.responseHeaders.getFirst('content-type') == 'application/pdf'
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

    void 'Client-Error when pdf is malformed'() {
        given:
        def pdf = new TestPDF()
        pdf.addPage(1)
        pdf.malformed = true
        httpExchange.requestBody = pdf.binary()

        when:
        handler.handle(httpExchange)

        then:
        noExceptionThrown()
        httpExchange.responseCode == 400
        httpExchange.responseHeaders.getFirst('content-type') ==~ 'text/plain'
        httpExchange.getResponseBodyAsText() ==~ '.*Malformed.*PDF.*'
    }

}
