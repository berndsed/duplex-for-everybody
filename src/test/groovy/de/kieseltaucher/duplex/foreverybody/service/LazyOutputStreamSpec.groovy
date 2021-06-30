package de.kieseltaucher.duplex.foreverybody.service

import spock.lang.Specification

import java.util.function.Supplier

class LazyOutputStreamSpec extends Specification {

    def 'opens new stream only once'() {
        given:
        def openCount = 0
        def source = {
            ++openCount
            new ByteArrayOutputStream()
        }
        def lazy = new LazyOutputStream(source)

        when:
        lazy.write 1
        then:
        openCount == 1

        when:
        lazy.write 1
        then:
        openCount == 1
    }

    def 'writes data to target'() {
        given:
        def target = new ByteArrayOutputStream()
        Supplier<OutputStream> source = { target }
        def lazy = new LazyOutputStream(source)

        when:
        lazy.write([1, 2] as byte[])
        lazy.write(3)
        lazy.write([4, 5] as byte[], 0, 1)

        then:
        target.toByteArray() == [1, 2, 3, 4] as byte[]
    }

    def 'flush is lazy'() {
        given:
        def target = Mock(OutputStream)
        def lazy = new LazyOutputStream({ target })

        when:
        lazy.flush()
        then:
        0 * target.flush()

        when:
        lazy.write 1
        lazy.flush()
        then:
        1 * target.flush()
    }

    def 'close is lazy'() {
        given:
        def target = Mock(OutputStream)

        when:
        new LazyOutputStream({target}).close()
        then:
        0 * target.close()

        when:
        def lazy = new LazyOutputStream({target})
        lazy.write 1
        lazy.close()
        then:
        1 * target.close()
    }

    def 'write after close throws exception'() {
        given:
        def lazy = new LazyOutputStream({ new ByteArrayOutputStream() })

        when:
        lazy.close()
        lazy.write 1

        then:
        thrown IOException
    }

}
