package com.arteger.diploma.data;

import java.util.List;

public abstract class AbstractGame {
    public abstract List<Player> getPlayers();
    public abstract void applyNature();
    public abstract int getRound();
    public abstract AbstractGame copy();
    public abstract void nextRound();
    public abstract boolean gameIsFinished();
}
