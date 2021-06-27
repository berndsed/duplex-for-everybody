package de.kieseltaucher.duplex.foreverybody.app.server

import groovy.cli.picocli.CliBuilder

class HttpCli {

    private final def cli = new CliBuilder(name: 'java -jar <jar-file-name>')

    Params parse(String[] args) {
        cli.parseFromSpec(Params, args)
    }

    void usage() {
        cli.usage()
    }

}
