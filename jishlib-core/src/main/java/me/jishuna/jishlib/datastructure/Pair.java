package me.jishuna.jishlib.datastructure;

public class Pair<L, R> extends MutablePair<L, R> {
    public Pair(L left, R right) {
        super(left, right);
    }

    @Override
    public void setLeft(L left) {
        throw new UnsupportedOperationException("Pair is immutable");
    }

    @Override
    public void setRight(R right) {
        throw new UnsupportedOperationException("Pair is immutable");
    }
}
