@groovy.lang.Grab(group = 'org.apache.pdfbox', module = 'pdfbox', version = '2.0.24')
import de.kieseltaucher.duplex.foreverybody.service.BatchService

def service = new BatchService()
service.simplex2Duplex(System.in, System.out)
System.out.flush()
