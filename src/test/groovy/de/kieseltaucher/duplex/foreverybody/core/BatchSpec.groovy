package de.kieseltaucher.duplex.foreverybody.core

import spock.lang.Specification

class BatchSpec extends Specification {

    def batch = new LinkedList<String>() as Batch<String>

    def 'preconditions'() {
        expect:
        batch == []
    }

    def 'batches in correct order do not change'() {

        when:
        batch.simplex2Duplex()
        then:
        batch == []

        and:
        batch.add('Page 1')
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1']

        and:
        batch.add('Page 2')
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1', 'Page 2']
    }

    def 'puts batch with 3 pages into correct order'() {
        given:
        batch.addAll(['Page 1', 'Page 3', 'Page 2'])
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1', 'Page 2', 'Page 3']
    }

    def 'puts batch with 4 pages into correct order'() {
        given:
        batch.addAll(['Page 1', 'Page 3', 'Page 4', 'Page 2'])
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1', 'Page 2', 'Page 3', 'Page 4']
    }

    def 'puts batch with 5 pages into correct order'() {
        given:
        batch.addAll(['Page 1', 'Page 3', 'Page 5', 'Page 4', 'Page 2'])
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1', 'Page 2', 'Page 3', 'Page 4', 'Page 5']
    }

    def 'puts batch with 6 pages into correct order'() {
        given:
        batch.addAll(['Page 1', 'Page 3', 'Page 5', 'Page 6', 'Page 4', 'Page 2'])
        when:
        batch.simplex2Duplex()
        then:
        batch == ['Page 1', 'Page 2', 'Page 3', 'Page 4', 'Page 5', 'Page 6']
    }

}
