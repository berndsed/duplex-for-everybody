package de.kieseltaucher.duplex.foreverybody.service

class MalformedPDFException extends IllegalArgumentException {

    MalformedPDFException(IOException cause) {
        super(cause)
    }

}
