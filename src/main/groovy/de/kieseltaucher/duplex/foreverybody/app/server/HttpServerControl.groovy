package de.kieseltaucher.duplex.foreverybody.app.server

import com.sun.net.httpserver.HttpServer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HttpServerControl {

    private HttpServer server
    private ExecutorService executorService

    HttpServerControl() {
        Runtime.getRuntime().addShutdownHook { stop() }
    }

    void start() {
        if (server != null) {
            return
        }

        println "Starting http server"
        server = HttpServer.create(new InetSocketAddress(8080), 0)
        executorService = Executors.newCachedThreadPool()
        server.with {
            createContext('/simplex-2-duplex', new HttpServerHandler())
            setExecutor(executorService)
            start()
        }
        println "Http server started"
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
