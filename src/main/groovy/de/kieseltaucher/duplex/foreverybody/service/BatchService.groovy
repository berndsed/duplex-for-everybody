package de.kieseltaucher.duplex.foreverybody.service

import de.kieseltaucher.duplex.foreverybody.core.Batch

class BatchService {

    void simplex2Duplex(InputStream simplex, OutputStream duplex) {
        def batch = new LinkedList() as Batch<String>
        batch.addAll simplex.getText().split()
        batch.simplex2Duplex()

        def rendered = batch.join " "

        def writer = new OutputStreamWriter(duplex)
        writer.write rendered
        writer.flush()
    }

}
