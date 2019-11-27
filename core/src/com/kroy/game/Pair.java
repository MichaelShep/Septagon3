package com.kroy.game;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(F first, S second) {
        //Constructs pair object;
        this.first = first;
        this.second = second;
    }

    //getters
    public F getFirst() {
        return first;
    }
    public S getSecond() {
        return second;
    }

    //setters
    public void setFirst(F first) {
        this.first = first;
    }
    public void setSecond(S second) {
        this.second = second;
    }

}