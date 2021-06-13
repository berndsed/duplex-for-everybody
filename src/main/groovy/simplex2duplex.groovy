import de.kieseltaucher.duplex.foreverybody.core.Batch

def batch = new LinkedList() as Batch<String>
batch.addAll System.in.getText().split()
batch.simplex2Duplex()

System.out.println batch.join(" ")
