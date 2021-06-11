package de.kieseltaucher.duplex.foreverybody.core

trait Batch<T> {

    abstract void add(int idx, T element)
    abstract T remove(int idx);
    abstract int size();

    void simplex2Duplex() {
        final int size = size()
        final boolean odd = size % 2 == 1
        final int idxBackPages = (size + (odd ? 1 : 0)) / 2

        int insertIdx = idxBackPages - (odd ? 1 : 0)
        int removeIdx = idxBackPages
        while(removeIdx < size) {
            def evenPage = remove(removeIdx)
            add(insertIdx, evenPage)
            ++removeIdx
            --insertIdx
        }
    }

}
