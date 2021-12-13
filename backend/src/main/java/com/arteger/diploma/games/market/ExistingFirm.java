package com.arteger.diploma.games.market;

import com.arteger.diploma.data.Player;
import com.arteger.diploma.data.Turn;

import java.util.HashSet;
import java.util.Set;

public class ExistingFirm extends Player<MarketGame> {
    @Override
    public Double getIncome(MarketGame game) {
        if (!game.isNewFirmInMarket()) {
            return 7.0;
        }
        if (game.isNewFirmAggressive()) {
            if (game.isExistingFirmAggressive()) {
                return -3.0;
            } else {
                return -1.0;
            }
        } else {
            if (game.isExistingFirmAggressive()) {
                return -2.0;
            } else {
                return 3.0;
            }
        }
    }

    @Override
    public Set<Turn> getPossibleTurns(MarketGame game) {
        Set<Turn> turns = new HashSet<>();
        turns.add(new Turn<MarketGame>() {
            @Override
            public String getName() {
                return "Be aggressive";
            }

            @Override
            public void apply(MarketGame game) {
                game.setExistingFirmAggressive(true);
            }
        });
        turns.add(new Turn<MarketGame>() {
            @Override
            public String getName() {
                return "Be peaceful";
            }

            @Override
            public void apply(MarketGame game) {

            }
        });
        return turns;
    }

    @Override
    public String getTag() {
        return "Existing firm";
    }
}
