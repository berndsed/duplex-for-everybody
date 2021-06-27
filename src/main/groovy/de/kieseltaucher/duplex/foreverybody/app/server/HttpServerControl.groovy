package de.kieseltaucher.duplex.foreverybody.app.server

import com.sun.net.httpserver.HttpServer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HttpServerControl {

    private static final int DEFAULT_PORT = 8080

    static void main(String[] args) {
        def params = new HttpCli().parse(args)
        def control = new HttpServerControl(params)
        control.start()
    }

    private HttpServer server
    private ExecutorService executorService
    private int port

    HttpServerControl(Params params) {
        this.port = params.port() > 0 ? params.port() : DEFAULT_PORT
        Runtime.getRuntime().addShutdownHook { stop() }
    }

    void start() {
        if (server != null) {
            return
        }

        println "Starting http server listening on port ${port}"

        server = HttpServer.create new InetSocketAddress(port), 0
        executorService = Executors.newCachedThreadPool()
        server.with {
            createContext '/simplex-2-duplex', new HttpServerHandler()
            setExecutor executorService
            start()
        }

        println "Http server started"
        println "POST to http://localhost:${port}/simplex-2-duplex in order to convert pdfs"
    }

    void stop() {
        if (server == null) {
            return
        }

        def toStop = server
        def toShutdown = executorService
        server = null
        executorService = null

        println "Two resources to shutdown"

        println "Stopping http server (0 of 2 shutdown)"
        toStop.stop 3
        println "Http server stopped (1 of 2 shutdown)"

        println "Shutting down executor service (1 of 2 shutdown)"
        toShutdown.shutdown()
        println "Executor service shut down (2 of 2 shutdown)"

        println "Bye"
    }

}
