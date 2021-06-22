@Grab(group = 'org.apache.pdfbox', module = 'pdfbox', version = '2.0.24')
import de.kieseltaucher.duplex.foreverybody.app.server.HttpServerControl

def server = new HttpServerControl()
server.start()

println ""
println "Press Ctrl+C to stop server"
