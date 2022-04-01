package Investments.Bolt.QuantConnectDeploy;

import java.util.HashMap;
import java.util.HashSet;

class WideNextDayPortfolio {


    // private Instance variable
    private HashMap<String, Float> widePortfolio;

    // Initializer block
    {
        this.widePortfolio = new HashMap<>();
    }

    // Constructors
    WideNextDayPortfolio(ShortNextDayPortfolio shortNextDayPortfolio, TickerChooser tickerChooser) {
        HashSet<String> wideTickers = tickerChooser.getTickers();
        HashMap<String, Float> shortPortfolio = shortNextDayPortfolio.getCutPortfolio();
        wideTickers.forEach((ticker) -> {
            this.widePortfolio.put(ticker, shortPortfolio.getOrDefault(ticker, 0F));
        });
    }

    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    public HashMap<String, Float> getWidePortfolio() {
        return widePortfolio;
    }
}
