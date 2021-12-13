package com.arteger.diploma.games.market;

import com.arteger.diploma.data.Player;
import com.arteger.diploma.data.Turn;
import com.arteger.diploma.games.market.turns.EnterTheMarket;
import com.arteger.diploma.games.market.turns.NotEnterTheMarket;

import java.util.HashSet;
import java.util.Set;

public class NewFirm extends Player<MarketGame> {
    @Override
    public Double getIncome(MarketGame game) {
        if (!game.isNewFirmInMarket()) {
            return 0.0;
        }
        if (game.isNewFirmAggressive()) {
            if (game.isExistingFirmAggressive()) {
                return -3.0;
            } else {
                return -2.0;
            }
        } else {
            if (game.isExistingFirmAggressive()) {
                return -1.0;
            } else {
                return 3.0;
            }
        }
    }

    @Override
    public Set<Turn> getPossibleTurns(MarketGame game) {
        HashSet<Turn> turns = new HashSet<>();
        if (game.getRound() == 0) {
            turns.add(new EnterTheMarket());
            turns.add(new NotEnterTheMarket());
        } else {
            turns.add(new Turn<MarketGame>() {
                @Override
                public String getName() {
                    return "Be aggressive";
                }

                @Override
                public void apply(MarketGame game) {
                    game.setNewFirmAggressive(true);
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
        }
        return turns;
    }

    @Override
    public String getTag() {
        return "New firm";
    }
}
