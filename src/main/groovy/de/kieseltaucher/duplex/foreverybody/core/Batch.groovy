package de.kieseltaucher.duplex.foreverybody.core

trait Batch<T> {

    abstract void add(int idx, T element)
    abstract T remove(int idx);
    abstract int size();

    void simplex2Duplex() {
        final int size = size()
        final boolean odd = size % 2 == 1

        /*
         * Given there is a scan with five pages (an odd number):
         *
         * [Page 1, Page 3, Page 5, Page 4, Page 2]
         *
         * The index of the first even page is 3.
         *
         * The algorithm needs to move the page at index 3 (Page 4) to index 2.
         * Next the algorithm needs move the page at index 4 to index 1.
         *
         * Given there is a scan with four pages (an even number):
         * [Page 1, Page 3, Page 4, Page 2]
         *
         * The index of the first even page is 2.
         *
         * The algorithm does not needs to move the page at index 2 (Page 4).
         * It is the last page, and may stay after Page 3.
         *
         * But it needs to move all subsequent pages.
         * In this example it needs to move the page at index 3 (Page 2) to index 1 (after Page 1).
         *
         */

        final int idxBackPages = (size + (odd ? 1 : 0)) / 2

        int insertIdx = idxBackPages - 1
        int removeIdx = idxBackPages + (odd ? 0 : 1)
        while(removeIdx < size) {
            def evenPage = remove(removeIdx)
            add(insertIdx, evenPage)
            ++removeIdx
            --insertIdx
        }
    }

}
