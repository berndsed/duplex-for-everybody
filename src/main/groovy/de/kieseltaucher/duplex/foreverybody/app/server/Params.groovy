package de.kieseltaucher.duplex.foreverybody.app.server

import groovy.cli.Option
import picocli.CommandLine

@CommandLine.Command(name = 'java -jar')
interface Params {
    @Option(shortName = 'p', longName = 'port', description = 'The port the http server shall listen to')
    Integer port()
    @Option(shortName = 'h', longName =  'help', description = 'Print this usage info')
    Boolean help()
}
