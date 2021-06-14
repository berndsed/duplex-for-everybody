import de.kieseltaucher.duplex.foreverybody.service.BatchService

def service = new BatchService()
service.simplex2Duplex(System.in, System.out)
System.out.println()
