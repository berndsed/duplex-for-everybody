package de.kieseltaucher.duplex.foreverybody.app.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import de.kieseltaucher.duplex.foreverybody.service.BatchService
import groovy.json.JsonParserType
import groovy.json.JsonSlurper

class AWSLambdaHandler implements RequestStreamHandler {

    private final BatchService service = new BatchService()
    private final JsonSlurper jsonParser = new JsonSlurper(type: JsonParserType.INDEX_OVERLAY)

    @Override
    void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        def requestBody = parseRequestBody(input)
        def serviceResult = simplex2Duplex(requestBody)
        writeResponse(serviceResult, output)
    }

    private String parseRequestBody(InputStream input) {
        def apiGatewayRequest = jsonParser.parse(input) as Map
        apiGatewayRequest.get('body') as String
    }

    private String simplex2Duplex(String simplex) {
        def serviceIn = new ByteArrayInputStream(simplex.bytes)
        def serviceOut = new ByteArrayOutputStream()

        service.simplex2Duplex(serviceIn, serviceOut)

        def serviceResult = new InputStreamReader(new ByteArrayInputStream(serviceOut.toByteArray())).text
        serviceResult
    }

    private void writeResponse(String duplex, OutputStream target) {
        def apiGatewayResponse = '{"headers": {"content-type": "text/plain"}, "body":"' + duplex + '"}'
        def writer = new OutputStreamWriter(target)
        writer.write(apiGatewayResponse)
        writer.close()
    }

}
