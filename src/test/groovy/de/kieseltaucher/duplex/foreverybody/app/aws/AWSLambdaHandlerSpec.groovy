package de.kieseltaucher.duplex.foreverybody.app.aws

import groovy.json.JsonSlurper
import spock.lang.Specification

class AWSLambdaHandlerSpec extends Specification {

    def handler = new AWSLambdaHandler()
    def data = ''

    def 'preconditions'() {
        when:
        doRequest()
        then:
        noExceptionThrown()

        expect:
        doRequest() != null
    }

    def 'when body is empty creates empty successful response'() {
        expect:
        doRequest() == ['headers': ['content-type': 'text/plain'],
                        'body': '']
    }

    def 'sorts batch'() {
        when:
        data = '1 3 2'
        def result = doRequest()
        then:
        result.get('body') == '1 2 3'
    }

    private Map<String, String> doRequest() {
        def httpApiRequest = '{"body": "' + data + '"}'
        def out = new ByteArrayOutputStream()
        handler.handleRequest(new ByteArrayInputStream(httpApiRequest.bytes), out, new Object() as ContextStub)
        def resultReader = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()))
        def parser = new JsonSlurper()
        parser.parseText resultReader.text
    }

}
