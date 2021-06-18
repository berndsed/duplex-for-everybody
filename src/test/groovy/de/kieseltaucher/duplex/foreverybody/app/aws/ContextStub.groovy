package de.kieseltaucher.duplex.foreverybody.app.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger

trait ContextStub implements Context {

    @Override
    LambdaLogger getLogger() {
        new LambdaLoggerStub()
    }
}
