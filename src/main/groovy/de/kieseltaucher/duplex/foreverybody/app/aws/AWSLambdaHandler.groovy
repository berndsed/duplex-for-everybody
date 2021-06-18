package de.kieseltaucher.duplex.foreverybody.app.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import de.kieseltaucher.duplex.foreverybody.service.BatchService
import groovy.json.JsonSlurper

class AWSLambdaHandler implements RequestStreamHandler {

    private final BatchService service = new BatchService()

    @Override
    void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        def jsonParser = new JsonSlurper()
        def apiGatewayRequest = jsonParser.parse(new InputStreamReader(input)) as Map
        def requestBody = apiGatewayRequest.get('body') as String

        def serviceIn = new ByteArrayInputStream(requestBody.bytes)
        def serviceOut = new ByteArrayOutputStream()

        service.simplex2Duplex(serviceIn, serviceOut)

        def serviceResult = new InputStreamReader(new ByteArrayInputStream(serviceOut.toByteArray())).text

        def apiGatewayResponse = '{"headers": {"content-type": "text/plain"}, "body":"' + serviceResult + '"}'

        def writer = new OutputStreamWriter(output)
        writer.write(apiGatewayResponse)
        writer.close()
    }

}
