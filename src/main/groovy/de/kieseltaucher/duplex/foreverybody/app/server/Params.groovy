package de.kieseltaucher.duplex.foreverybody.app.server

import groovy.cli.Option

interface Params {
    @Option(shortName = 'p', longName = 'port')
    Integer port()
}
