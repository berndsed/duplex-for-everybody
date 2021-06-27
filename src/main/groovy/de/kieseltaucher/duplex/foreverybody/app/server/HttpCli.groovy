package de.kieseltaucher.duplex.foreverybody.app.server

import groovy.cli.picocli.CliBuilder

class HttpCli {

    private final def cli = new CliBuilder()

    Params parse(String[] args) {
        cli.parseFromSpec(Params, args)
    }

}
