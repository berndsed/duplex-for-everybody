package de.kieseltaucher.duplex.foreverybody.core

import spock.lang.Specification

class BatchSpec extends Specification {

    def batch = new TestBatch()

    def 'preconditions'() {
        expect:
        batch == []
    }

    def 'empty batch does not change'() {
        when:
        batch.simplex2Duplex()
        then:
        batch == []
    }

    def 'batch with one page does not change'() {
        given:
        batch.add('Page 1')
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1']
    }

    def 'batch with two pages does not change'() {
        given:
        batch.add('Page 1')
        batch.add('Page 2')
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1', 'Page 2']
    }

}

class TestBatch extends LinkedList<String> implements Batch<String> {

}
