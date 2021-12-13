package com.arteger.diploma.data;

public abstract class Turn<G extends AbstractGame> {
    public abstract String getName();

    public abstract void apply(G game);
}
