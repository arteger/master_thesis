package com.arteger.diploma.games.market.turns;

import com.arteger.diploma.data.Turn;
import com.arteger.diploma.games.market.MarketGame;

public class EnterTheMarket extends Turn<MarketGame> {
    @Override
    public String getName() {
        return "Enter the market";
    }

    @Override
    public void apply(MarketGame game) {
        game.setNewFirmInMarket(true);
    }
}
