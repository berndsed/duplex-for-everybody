package de.kieseltaucher.duplex.foreverybody.app.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import de.kieseltaucher.duplex.foreverybody.service.BatchService
import groovy.json.JsonOutput
import groovy.json.JsonParserType
import groovy.json.JsonSlurper

class AWSLambdaHandler implements RequestStreamHandler {

    private final BatchService service = new BatchService()
    private final JsonSlurper jsonParser = new JsonSlurper(type: JsonParserType.INDEX_OVERLAY)
    private final Base64.Decoder base64Decoder = Base64.getDecoder()
    private final Base64.Encoder base64Encoder = Base64.getEncoder()

    @Override
    void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        def pdfIn = parseRequestBody(input)
        def pdfOut = simplex2Duplex(pdfIn)
        writeResponse(pdfOut, output)
    }

    private byte[] parseRequestBody(InputStream input) {
        def apiGatewayRequest = jsonParser.parse(input) as Map
        def base64Body = apiGatewayRequest.get('body') as String
        base64Decoder.decode base64Body
    }

    private byte[] simplex2Duplex(byte[] simplex) {
        def serviceOut = new ByteArrayOutputStream()
        service.simplex2Duplex(new ByteArrayInputStream(simplex), serviceOut)
        serviceOut.toByteArray()
    }

    private void writeResponse(byte[] duplex, OutputStream target) {
        def apiGatewayResponse = JsonOutput.toJson([
                'headers': ['content-type': 'application/pdf'],
                'body'   : base64Encoder.encodeToString(duplex),
                'isBase64Encoded': true
        ])
        def writer = new OutputStreamWriter(target)
        writer.write(apiGatewayResponse)
        writer.close()
    }

}
