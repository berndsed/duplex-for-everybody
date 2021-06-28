package de.kieseltaucher.duplex.foreverybody.app.script

import picocli.CommandLine
import spock.lang.Specification

import java.util.function.Consumer

class ScriptCliSpec extends Specification {

    def 'no options'() {
        when:
        def result = execute()
        then:
        result.exitCode == 0
        result.file == null
    }

    def 'one file'() {
        when:
        def result = execute('the-path-to-the-file')
        then:
        result.exitCode == 0
        result.file == new File('the-path-to-the-file')

    }

    def 'two files are invalid'() {
        when:
        def result = execute('file1', 'file2')
        then:
        result.exitCode == 2
    }

    def 'unknown option'() {
        expect:
        execute('--unknown').exitCode == 2
    }

    private InvocationResult execute(String... args) {
        def invocationResult = new InvocationResult()
        def service = new Consumer<File>() {
            @Override
            void accept(File file) {
                invocationResult.file = file
            }
        }
        def app = new ScriptCli(service)
        def cmd = new CommandLine(app)
        invocationResult.exitCode = cmd.execute(args)
        invocationResult
    }

    private static class InvocationResult {
        int exitCode
        File file
    }

}
