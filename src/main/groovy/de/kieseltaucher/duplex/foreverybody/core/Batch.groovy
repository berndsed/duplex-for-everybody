package de.kieseltaucher.duplex.foreverybody.core

trait Batch<T> {

    abstract void add(int idx, T element)
    abstract T remove(int idx);
    abstract int size();

    void simplex2Duplex() {
    }

}
