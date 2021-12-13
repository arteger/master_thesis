package com.arteger.diploma.games.market;

import com.arteger.diploma.data.AbstractGame;
import com.arteger.diploma.data.Player;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class MarketGame extends AbstractGame {
    ExistingFirm existingFirm;
    NewFirm newFirm;
    private boolean newFirmInMarket = false;
    private boolean newFirmAggressive = false;
    private boolean existingFirmAggressive = false;
    private int round;

    public MarketGame() {
        this.existingFirm = new ExistingFirm();
        this.newFirm = new NewFirm();
    }

    @Override
    public List<Player> getPlayers() {
        return Arrays.asList(newFirm,newFirm, existingFirm);
    }

    @Override
    public void applyNature() {

    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public AbstractGame copy() {
        MarketGame marketGame = new MarketGame();
        marketGame.existingFirm = existingFirm;
        marketGame.newFirm = newFirm;
        marketGame.newFirmInMarket = newFirmInMarket;
        marketGame.existingFirmAggressive = existingFirmAggressive;
        marketGame.newFirmAggressive = newFirmAggressive;
        marketGame.round = round;
        return marketGame;
    }

    @Override
    public boolean gameIsFinished() {
            return !newFirmInMarket || round == 3;
    }

    public void nextRound() {
        round++;
    }
}
