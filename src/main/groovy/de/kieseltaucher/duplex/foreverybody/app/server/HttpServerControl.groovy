package de.kieseltaucher.duplex.foreverybody.app.server

import com.sun.net.httpserver.HttpServer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HttpServerControl {

    private HttpServer server
    private ExecutorService executorService
    private int port = 8080

    HttpServerControl() {
        Runtime.getRuntime().addShutdownHook { stop() }
    }

    void start() {
        if (server != null) {
            return
        }

        println "Starting http server listening on port ${port}"
        server = HttpServer.create(new InetSocketAddress(port), 0)
        executorService = Executors.newCachedThreadPool()
        server.with {
            createContext('/simplex-2-duplex', new HttpServerHandler())
            setExecutor(executorService)
            start()
        }
        println "Http server started"
        println "Post to http://localhost:${port}/simplex-2-duplex"
    }

    void stop() {
        if (server == null) {
            return
        }

        def toStop = server
        def toShutdown = executorService
        server = null
        executorService = null

        println "Stopping http server"
        toStop.stop(3)
        println "Http server stopped"

        println "Shutting down executor service"
        toShutdown.shutdown()
        println "Executor service shut down"
    }

}
