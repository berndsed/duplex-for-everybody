package de.kieseltaucher.duplex.foreverybody.app.server

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpContext
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpPrincipal
import de.kieseltaucher.duplex.foreverybody.service.TestPDF

class TestHttpExchange extends HttpExchange {

    String requestMethod = 'POST'
    Headers requestHeaders = new Headers()
    InputStream requestBody = TestPDF.empty()
    int responseCode
    Headers responseHeaders = new Headers()
    OutputStream responseBody = new ByteArrayOutputStream()

    String getResponseBodyAsText() {
        return new String(responseBody.toByteArray())
    }

    @Override
    URI getRequestURI() {
        return null
    }

    @Override
    HttpContext getHttpContext() {
        return null
    }

    @Override
    void close() {
        requestBody.close()
        responseBody.close()
    }

    @Override
    void sendResponseHeaders(int rCode, long responseLength) throws IOException {
        responseCode = rCode
    }

    @Override
    InetSocketAddress getRemoteAddress() {
        return null
    }

    @Override
    InetSocketAddress getLocalAddress() {
        return null
    }

    @Override
    String getProtocol() {
        return null
    }

    @Override
    Object getAttribute(String name) {
        return null
    }

    @Override
    void setAttribute(String name, Object value) {
    }

    @Override
    void setStreams(InputStream i, OutputStream o) {
    }

    @Override
    HttpPrincipal getPrincipal() {
        return null
    }
}
