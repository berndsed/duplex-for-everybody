package de.kieseltaucher.duplex.foreverybody.app.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import de.kieseltaucher.duplex.foreverybody.service.BatchService
import groovy.json.JsonOutput
import groovy.json.JsonParserType
import groovy.json.JsonSlurper

import static java.nio.charset.StandardCharsets.ISO_8859_1

class AWSLambdaHandler implements RequestStreamHandler {

    private final BatchService service = new BatchService()
    private final JsonSlurper jsonParser = new JsonSlurper(type: JsonParserType.INDEX_OVERLAY)
    private final Base64.Decoder base64Decoder = Base64.getDecoder()
    private final Base64.Encoder base64Encoder = Base64.getEncoder()

    @Override
    void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        def pdfOut = simplex2Base64Duplex parseSimplex(input)
        writeResponse pdfOut, output
    }

    private byte[] parseSimplex(InputStream input) {
        def apiGatewayRequest = parseJson input
        def base64Body = apiGatewayRequest.get('body') as String
        base64Decoder.decode base64Body
    }

    private Map parseJson(InputStream input) {
        def bufferedIn = new BufferedInputStream(input)
        try {
            jsonParser.parse(input) as Map
        } finally {
            bufferedIn.close()
        }
    }

    private String simplex2Base64Duplex(byte[] simplex) {
        def base64Out = new ByteArrayOutputStream()
        service.simplex2Duplex new ByteArrayInputStream(simplex), base64Encoder.wrap(base64Out)
        new String(base64Out.toByteArray(), ISO_8859_1)
    }

    private void writeResponse(String duplex, OutputStream target) {
        def apiGatewayResponse = JsonOutput.toJson([
                'headers': ['content-type': 'application/pdf'],
                'body'   : duplex,
                'isBase64Encoded': true
        ])
        def writer = new OutputStreamWriter(target)
        writer.write apiGatewayResponse
        writer.close()
    }

}
