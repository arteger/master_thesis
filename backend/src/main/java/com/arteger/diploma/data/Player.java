package com.arteger.diploma.data;

import java.util.Set;

public abstract class Player<G extends AbstractGame> {
    public abstract Double getIncome(G game);

    public abstract Set<Turn> getPossibleTurns(G game);

    public abstract String getTag();
}
