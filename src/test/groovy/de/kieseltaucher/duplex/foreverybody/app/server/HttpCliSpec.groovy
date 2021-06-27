package de.kieseltaucher.duplex.foreverybody.app.server

import spock.lang.Specification

class HttpCliSpec extends Specification {

    def cli = new HttpCli()

    def 'default values'() {
        when:
        def params = cli.parse([] as String[])
        then:
        params.port() == null
    }

    def 'port option'() {
        expect:
        cli.parse(['--port', '1'] as String[]).port() == 1
        cli.parse(['-p', '1'] as String[]).port() == 1
    }

}
