package com.arteger.diploma.games.market.turns;

import com.arteger.diploma.data.Turn;
import com.arteger.diploma.games.market.MarketGame;

public class NotEnterTheMarket extends Turn<MarketGame> {
    @Override
    public String getName() {
        return "Not enter the market";
    }

    @Override
    public void apply(MarketGame game) {
        game.setNewFirmInMarket(false);
    }
}
