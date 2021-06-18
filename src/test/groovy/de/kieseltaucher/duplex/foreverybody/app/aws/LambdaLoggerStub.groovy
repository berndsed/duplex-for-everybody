package de.kieseltaucher.duplex.foreverybody.app.aws

import com.amazonaws.services.lambda.runtime.LambdaLogger

class LambdaLoggerStub implements LambdaLogger {

    @Override
    void log(String message) {
        println "Lambda Logger(String): ${message}"
    }

    @Override
    void log(byte[] message) {
        println "Lambda Logger(byte[]): ${message.length} bytes"
    }
}
