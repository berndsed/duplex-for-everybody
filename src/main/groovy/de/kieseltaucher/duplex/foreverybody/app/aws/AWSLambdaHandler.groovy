package de.kieseltaucher.duplex.foreverybody.app.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import de.kieseltaucher.duplex.foreverybody.service.BatchService

class AWSLambdaHandler implements RequestStreamHandler {

    private final BatchService service = new BatchService()

    @Override
    void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        service.simplex2Duplex(input, output)
    }

}
