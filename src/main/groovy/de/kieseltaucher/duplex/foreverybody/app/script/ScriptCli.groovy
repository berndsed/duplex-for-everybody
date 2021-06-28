package de.kieseltaucher.duplex.foreverybody.app.script

import picocli.CommandLine

import java.util.function.Consumer

@CommandLine.Command(name = 'groovy simplex-2-duplex.groovy')
class ScriptCli implements Runnable {

    @CommandLine.Parameters(arity = '0..1', description = "file to convert")
    private File file

    private final Consumer<File> service

    ScriptCli(Consumer<File> service) {
        this.service = service
    }

    @Override
    void run() {
        service.accept(file)
    }

    static void main(String[] args) {
        def cli = new ScriptCli(new ScriptService())
        int exitCode = new CommandLine(cli).execute(args)
        System.exit(exitCode)
    }
}
