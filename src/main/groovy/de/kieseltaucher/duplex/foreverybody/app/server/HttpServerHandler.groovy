package de.kieseltaucher.duplex.foreverybody.app.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import de.kieseltaucher.duplex.foreverybody.service.BatchService
import de.kieseltaucher.duplex.foreverybody.service.LazyOutputStream
import de.kieseltaucher.duplex.foreverybody.service.MalformedPDFException
import org.apache.pdfbox.util.Charsets

class HttpServerHandler implements HttpHandler {

    private final BatchService service = new BatchService()

    @Override
    void handle(HttpExchange exchange) throws IOException {
        try {
            if (!isPost(exchange)) {
                respondWrongMethod(exchange)
            } else {
                respondDuplexPdf(exchange)
            }
        } catch (MalformedPDFException e) {
            respondMalformedPdf(exchange, e)
        } finally {
            exchange.close()
        }
    }

    private void respondDuplexPdf(HttpExchange exchange) {
        def responseSource = {
            exchange.responseHeaders.set 'content-type', 'application/pdf'
            exchange.sendResponseHeaders 200, 0
            exchange.getResponseBody()
        }
        service.simplex2Duplex new BufferedInputStream(exchange.requestBody), new LazyOutputStream(responseSource)
    }

    private void respondMalformedPdf(HttpExchange exchange, MalformedPDFException e) {
        exchange.responseHeaders.set 'content-type', 'text/plain'
        def message = "Malformed PDF: ${e.getMessage()}"
        def body = message.getBytes Charsets.ISO_8859_1
        exchange.sendResponseHeaders 400, body.length
        exchange.responseBody.write body
    }

    private boolean isPost(HttpExchange exchange) {
        exchange.requestMethod == 'POST'
    }

    private respondWrongMethod(HttpExchange exchange) {
        exchange.sendResponseHeaders 405, -1
    }

}
