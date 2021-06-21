package de.kieseltaucher.duplex.foreverybody.app.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import de.kieseltaucher.duplex.foreverybody.service.BatchService

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
        } finally {
            exchange.close()
        }
    }

    private void respondDuplexPdf(HttpExchange exchange) {
        def out = new BufferedOutputStream(exchange.responseBody)
        exchange.responseHeaders.set('content-type', 'application/pdf')
        exchange.sendResponseHeaders(200, 0)
        service.simplex2Duplex(new BufferedInputStream(exchange.requestBody), out)
    }

    private boolean isPost(HttpExchange exchange) {
        exchange.requestMethod == 'POST'
    }

    private respondWrongMethod(HttpExchange exchange) {
        exchange.sendResponseHeaders(405, -1)
    }
}
